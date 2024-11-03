package ru.clevertec.cacheservice.cache;

import org.springframework.cache.Cache;

import java.util.*;
import java.util.concurrent.Callable;

/**
 * Реализация кэша с использованием стратегии LFU.
 *
 * <p>Этот класс управляет кэшированием значений, отслеживая частоту доступа к ним
 * и удаляя наименее часто используемые элементы при необходимости.</p>
 */
public class LFUCache implements Cache {

    /**
     * Имя кэша.
     */
    private final String name;

    /**
     * Вместимость кэша.
     */
    private final int capacity;

    /**
     * Указывает, разрешены ли значения null в кэше.
     */
    private final boolean allowNullValues;

    /**
     * Хранилище для кэшируемых значений.
     */
    private final Map<Object, Object> cache;

    /**
     * Частота доступа к кэшируемым значениям.
     */
    private final Map<Object, Integer> frequencyMap;

    /**
     * Связь частоты доступа с набором ключей.
     */
    private final Map<Integer, Set<Object>> frequencyList;

    /**
     * Минимальная частота доступа среди текущих элементов кэша.
     */
    private int minFrequency;

    /**
     * Конструктор, создающий LFUCache с заданным именем.
     *
     * @param name Имя кэша.
     */
    public LFUCache(String name) {
        this(name, 256, false);
    }

    /**
     * Конструктор, создающий LFUCache с заданным именем и параметрами.
     *
     * @param name Имя кэша.
     * @param allowNullValues Указывает, разрешены ли значения null в кэше.
     */
    public LFUCache(String name, boolean allowNullValues) {
        this(name, 256, allowNullValues);
    }

    /**
     * Конструктор, создающий LFUCache с заданным именем и вместимостью.
     *
     * @param name Имя кэша.
     * @param capacity Вместимость кэша.
     */
    public LFUCache(String name, int capacity) {
        this(name, capacity, false);
    }

    /**
     * Конструктор, создающий LFUCache с заданным именем, вместимостью и параметрами.
     *
     * @param name Имя кэша.
     * @param capacity Вместимость кэша.
     * @param allowNullValues Указывает, разрешены ли значения null в кэше.
     */
    public LFUCache(String name, int capacity, boolean allowNullValues) {
        this.name = name;
        this.capacity = capacity;
        this.allowNullValues = allowNullValues;
        this.cache = new HashMap<>();
        this.frequencyMap = new HashMap<>();
        this.frequencyList = new HashMap<>();
        this.minFrequency = 0;
    }

    /**
     * Получает имя кэша.
     *
     * @return Имя кэша.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Получает доступ к нативному хранилищу кэша.
     *
     * @return Нативное хранилище кэша.
     */
    @Override
    public Object getNativeCache() {
        return cache;
    }

    /**
     * Получает значение по ключу.
     *
     * @param key Ключ для поиска.
     * @return Обертка значения, связанного с ключом, или {@code null}, если значение не найдено.
     */
    @Override
    public ValueWrapper get(Object key) {
        if (!cache.containsKey(key)) {
            return null;
        }
        updateFrequency(key);
        return () -> cache.get(key);
    }

    /**
     * Получает значение по ключу и приводит его к указанному типу.
     *
     * @param key Ключ для поиска.
     * @param type Ожидаемый тип значения.
     * @param <T> Тип значения.
     * @return Значение, связанное с ключом, или {@code null}, если значение не найдено.
     */
    @Override
    public <T> T get(Object key, Class<T> type) {
        ValueWrapper valueWrapper = get(key);
        if (valueWrapper != null) {
            return type.cast(valueWrapper.get());
        }
        return null;
    }

    /**
     * Получает значение по ключу, используя загрузчик значений, если значение отсутствует.
     *
     * @param key         Ключ для поиска.
     * @param valueLoader Загрузчик значений, вызываемый при отсутствии значения.
     * @param <T>         Тип значения.
     * @return Значение, связанное с ключом.
     * @throws RuntimeException Если загрузка значения завершилась неудачно.
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        ValueWrapper valueWrapper = get(key);
        if (valueWrapper != null) {
            return (T) valueWrapper.get();
        }
        try {
            T value = valueLoader.call();
            put(key, value);
            return value;
        } catch (Exception e) {
            throw new RuntimeException("ValueLoader failed", e);
        }
    }

    /**
     * Сохраняет значение в кэше по указанному ключу.
     *
     * @param key   Ключ для сохранения значения.
     * @param value Значение для сохранения.
     * @throws IllegalArgumentException Если значение равно null и null значения не разрешены.
     */
    @Override
    public void put(Object key, Object value) {
        if (capacity <= 0) {
            return;
        }

        if (value == null && !allowNullValues) {
            throw new IllegalArgumentException("Null values are not allowed");
        }

        if (cache.containsKey(key)) {
            cache.put(key, value);
            updateFrequency(key);
            return;
        }

        if (cache.size() >= capacity) {
            evict();
        }

        cache.put(key, value);
        frequencyMap.put(key, 1);
        frequencyList.computeIfAbsent(1, k -> new HashSet<>()).add(key);
        minFrequency = 1;
    }

    /**
     * Удаляет значение из кэша по ключу.
     *
     * @param key Ключ для удаления значения.
     */
    @Override
    public void evict(Object key) {
        if (!cache.containsKey(key)) {
            return;
        }

        int frequency = frequencyMap.get(key);
        frequencyMap.remove(key);
        cache.remove(key);
        frequencyList.get(frequency).remove(key);

        if (frequencyList.get(frequency).isEmpty()) {
            if (frequency == minFrequency) {
                minFrequency++;
            }
            frequencyList.remove(frequency);
        }
    }

    /**
     * Удаляет наименее часто используемые значения из кэша, если он переполнен.
     */
    private void evict() {
        Set<Object> keysToEvict = frequencyList.get(minFrequency);
        if (keysToEvict != null && !keysToEvict.isEmpty()) {
            Object keyToEvict = keysToEvict.iterator().next();
            evict(keyToEvict);
        }
    }

    /**
     * Очищает все значения в кэше.
     */
    @Override
    public void clear() {
        cache.clear();
        frequencyMap.clear();
        frequencyList.clear();
        minFrequency = 0;
    }

    /**
     * Обновляет частоту доступа к элементу.
     *
     * @param key Ключ элемента, частота которого должна быть обновлена.
     */
    private void updateFrequency(Object key) {
        int frequency = frequencyMap.get(key);
        frequencyMap.put(key, frequency + 1);
        frequencyList.get(frequency).remove(key);

        if (frequencyList.get(frequency).isEmpty()) {
            if (frequency == minFrequency) {
                minFrequency++;
            }
            frequencyList.remove(frequency);
        }

        frequencyList.computeIfAbsent(frequency + 1, k -> new HashSet<>()).add(key);
    }
}
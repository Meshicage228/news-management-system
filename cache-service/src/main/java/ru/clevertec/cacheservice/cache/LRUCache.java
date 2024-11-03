package ru.clevertec.cacheservice.cache;

import org.springframework.cache.support.AbstractValueAdaptingCache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Реализация кэша с использованием стратегии LRU.
 */
public class LRUCache extends AbstractValueAdaptingCache {

    /**
     * Имя кэша.
     */
    private final String name;

    /**
     * Хранилище для кэшируемых значений.
     */
    private Map<Object, Object> store;

    /**
     * Конструктор, создающий LRUCache с заданной вместимостью.
     *
     * @param capacity Вместимость кэша.
     * @param name Имя кэша.
     */
    public LRUCache(int capacity, String name) {
        this(true, name, capacity);
    }

    /**
     * Конструктор, создающий LRUCache с заданной вместимостью и параметрами.
     *
     * @param allowNullValues Указывает, разрешены ли значения null в кэше.
     * @param name Имя кэша.
     * @param capacity Вместимость кэша.
     */
    public LRUCache(boolean allowNullValues, String name, int capacity) {
        super(allowNullValues);
        this.name = name;

        this.store = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Object, Object> eldest) {
                return size() > capacity;
            }
        };
    }

    /**
     * Ищет значение по ключу в кэше.
     *
     * @param key Ключ для поиска.
     * @return Значение, связанное с ключом, или {@code null}, если значение не найдено.
     */
    @Override
    protected Object lookup(Object key) {
        return store.get(key);
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
        return store;
    }

    /**
     * Получает значение по ключу, используя загрузчик значений, если значение отсутствует.
     *
     * @param key Ключ для поиска.
     * @param valueLoader Загрузчик значений, вызываемый при отсутствии значения.
     * @return Значение, связанное с ключом.
     * @throws RuntimeException Если загрузка значения завершилась неудачно.
     */
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        Object value = store.get(key);
        if (value == null) {
            try {
                value = valueLoader.call();
                put(key, value);
            } catch (Exception e) {
                throw new RuntimeException("ValueLoader failed", e);
            }
        }
        return (T) value;
    }

    /**
     * Сохраняет значение в кэше по указанному ключу.
     *
     * @param key Ключ для сохранения значения.
     * @param value Значение для сохранения.
     * @throws IllegalArgumentException Если значение равно null и null значения не разрешены.
     */
    @Override
    public void put(Object key, Object value) {
        if (value == null && !isAllowNullValues()) {
            throw new IllegalArgumentException("Null values are not allowed");
        }
        store.put(key, value);
    }

    /**
     * Удаляет значение из кэша по ключу.
     *
     * @param key Ключ для удаления значения.
     */
    @Override
    public void evict(Object key) {
        store.remove(key);
    }

    /**
     * Очищает все значения в кэше.
     */
    @Override
    public void clear() {
        store.clear();
    }
}

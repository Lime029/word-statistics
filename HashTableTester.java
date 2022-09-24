import org.junit.Assert;
import org.junit.Test;
import java.lang.reflect.Field;
import java.util.LinkedList;

public class HashTableTester {
    private HashTable t1 = new HashTable();
    private HashTable t2 = new HashTable(0);

    @Test
    public void testConstructor() throws NoSuchFieldException, IllegalAccessException {
        Field field = t1.getClass().getDeclaredField("elements");
        field.setAccessible(true);
        Assert.assertEquals(1024, ((LinkedList<HashEntry>[])field.get(t1)).length);
        Assert.assertEquals(0, ((LinkedList<HashEntry>[])field.get(t2)).length);
    }

    @Test
    public void testPut() throws NoSuchFieldException, IllegalAccessException {
        Field field = t1.getClass().getDeclaredField("elements");
        field.setAccessible(true);
        LinkedList<HashEntry>[] t1Elements = ((LinkedList<HashEntry>[])field.get(t1));
        t1.put("d", 1);
        t1.put("e", 1);
        Assert.assertEquals("d", t1Elements["d".hashCode()].get(0).getKey());
        Assert.assertEquals(1, t1Elements["d".hashCode()].get(0).getValue());
        Assert.assertEquals("e", t1Elements["e".hashCode()].get(0).getKey());
        Assert.assertEquals(1, t1Elements["e".hashCode()].get(0).getValue());
        t1.put("e", 1);
        Assert.assertEquals(2, t1Elements["e".hashCode()].get(0).getValue());
        Assert.assertEquals(1, t1Elements["e".hashCode()].size());
        t1.put("f", 1, "e".hashCode());
        t1.put("g", 1, "e".hashCode());
        Assert.assertEquals("e", t1Elements["e".hashCode()].get(0).getKey());
        Assert.assertEquals("f", t1Elements["e".hashCode()].get(1).getKey());
        Assert.assertEquals("g", t1Elements["e".hashCode()].get(2).getKey());
        t2.put("d", 1);
        LinkedList<HashEntry>[] t2Elements = ((LinkedList<HashEntry>[])field.get(t2));
        Assert.assertEquals("d", t2Elements["d".hashCode()%t2Elements.length].get(0).getKey());
        Assert.assertEquals(1, t2Elements.length); // Properly rehashed
        Assert.assertEquals(1, t2Elements["d".hashCode()%t2Elements.length].size());
        t2.put("e", 1);
        t2Elements = ((LinkedList<HashEntry>[])field.get(t2));
        Assert.assertEquals("e", t2Elements["e".hashCode()%t2Elements.length].get(0).getKey());
        Assert.assertEquals(3, t2Elements.length); // Properly rehashed
        Assert.assertEquals(1, t2Elements["e".hashCode()%t2Elements.length].size());
        t2.put("f", 1, "e".hashCode());
        t2.put("f", 1, "e".hashCode());
        t2.put("g", 1, "e".hashCode());
        t2Elements = ((LinkedList<HashEntry>[])field.get(t2));
        Assert.assertEquals("f", t2Elements["e".hashCode()%t2Elements.length].get(1).getKey());
        Assert.assertEquals("g", t2Elements["e".hashCode()%t2Elements.length].get(2).getKey());
        Assert.assertEquals(7, t2Elements.length); // Properly rehashed
        Assert.assertEquals(3, t2Elements["e".hashCode()%t2Elements.length].size());
    }

    @Test
    public void testUpdate() throws NoSuchFieldException, IllegalAccessException {
        Field field = t1.getClass().getDeclaredField("elements");
        field.setAccessible(true);
        LinkedList<HashEntry>[] t1Elements = ((LinkedList<HashEntry>[])field.get(t1));
        t1.update("d", 1);
        t1.update("e", 1);
        Assert.assertEquals("d", t1Elements["d".hashCode()].get(0).getKey());
        Assert.assertEquals(1, t1Elements["d".hashCode()].get(0).getValue());
        Assert.assertEquals("e", t1Elements["e".hashCode()].get(0).getKey());
        Assert.assertEquals(1, t1Elements["e".hashCode()].get(0).getValue());
        t1.update("e", 1);
        Assert.assertEquals(1, t1Elements["e".hashCode()].get(0).getValue());
        Assert.assertEquals(1, t1Elements["e".hashCode()].size());
        t2.update("d", 1);
        LinkedList<HashEntry>[] t2Elements = ((LinkedList<HashEntry>[])field.get(t2));
        Assert.assertEquals("d", t2Elements["d".hashCode()%t2Elements.length].get(0).getKey());
        Assert.assertEquals(1, t2Elements.length); // Properly rehashed
        Assert.assertEquals(1, t2Elements["d".hashCode()%t2Elements.length].size());
        t2.update("e", 1);
        t2Elements = ((LinkedList<HashEntry>[])field.get(t2));
        Assert.assertEquals("e", t2Elements["e".hashCode()%t2Elements.length].get(0).getKey());
        Assert.assertEquals(3, t2Elements.length); // Properly rehashed
        Assert.assertEquals(1, t2Elements["e".hashCode()%t2Elements.length].size());
        t2.update("f", 1);
        t2.update("f", 3);
        t2.update("g", 1);
        t2Elements = ((LinkedList<HashEntry>[])field.get(t2));
        Assert.assertEquals("f", t2Elements["f".hashCode()%t2Elements.length].get(0).getKey());
        Assert.assertEquals(3, t2Elements["f".hashCode()%t2Elements.length].get(0).getValue());
        Assert.assertEquals("g", t2Elements["g".hashCode()%t2Elements.length].get(0).getKey());
        Assert.assertEquals(7, t2Elements.length); // Properly rehashed
    }

    @Test
    public void testGet() {
        Assert.assertEquals(-1, t1.get("nonexistent key"));
        Assert.assertEquals(-1, t2.get("nonexistent key"));
        t1.put("a", 1);
        t1.put("b", 2);
        Assert.assertEquals(1, t1.get("a"));
        Assert.assertEquals(2, t1.get("b"));
        t2.put("a", 1);
        Assert.assertEquals(1, t2.get("a"));
        t1.put("c", 3, "a".hashCode());
        Assert.assertEquals(3, t2.get("c"), "a".hashCode());
    }
}

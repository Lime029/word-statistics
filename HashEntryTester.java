import org.junit.Assert;
import org.junit.Test;

public class HashEntryTester {
    private HashEntry e1 = new HashEntry("str1", 1);
    private HashEntry e2 = new HashEntry("str2", 1);

    @Test
    public void testGetKey() {
        Assert.assertEquals("str1", e1.getKey());
    }

    @Test
    public void testGetValue() {
        Assert.assertEquals(1, e1.getValue());
    }

    @Test
    public void testSetValue() {
        e1.setValue(2);
        Assert.assertEquals(2, e1.getValue());
    }

    @Test
    public void testCompareTo() {
        Assert.assertEquals(0, e1.compareTo(e2));
        e1.setValue(2);
        Assert.assertEquals(-1, e1.compareTo(e2));
        e2.setValue(4);
        Assert.assertEquals(2, e1.compareTo(e2));
    }
}
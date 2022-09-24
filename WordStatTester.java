import org.junit.Test;
import org.junit.Assert;

public class WordStatTester {
    private WordStat stat1 = new WordStat("C:\\Users\\emile\\Desktop\\test1.txt");
    private WordStat stat2 = new WordStat("C:\\Users\\emile\\Desktop\\test2.txt");
    private WordStat stat3 = new WordStat("C:\\Users\\emile\\Desktop\\test3.txt");
    private WordStat stat4 = new WordStat("C:\\Users\\emile\\Desktop\\test4.txt");
    private WordStat stat5 = new WordStat("C:\\Users\\emile\\Desktop\\test5.txt");
    private WordStat stat6 = new WordStat(new String[]{"5I$^*"});
    private WordStat stat7 = new WordStat(new String[]{":::ITEM", "MODIFIERS:::", "modifiers", ";;;;:#*(@#&$", "352;;1"});
    private WordStat stat8 = new WordStat(new String[]{});
    private WordStat stat9 = new WordStat(new String[]{"one", "two", "one", "two", "one", "twotie", "three", "twotie",
                                                       "threetie", "threetietie"});

    @Test
    public void testWordCount() {
        Assert.assertEquals(1, stat1.wordCount("test"));
        Assert.assertEquals(0, stat1.wordCount(""));
        Assert.assertEquals(0, stat1.wordCount("t"));
        Assert.assertEquals(0, stat2.wordCount(""));
        Assert.assertEquals(0, stat2.wordCount("t"));
        Assert.assertEquals(2, stat3.wordCount("flash"));
        Assert.assertEquals(1, stat3.wordCount("storm"));
        Assert.assertEquals(0, stat3.wordCount("world"));
        Assert.assertEquals(12, stat3.wordCount("following"));
        Assert.assertEquals(13, stat3.wordCount("moves"));
        Assert.assertEquals(25, stat3.wordCount("the"));
        Assert.assertEquals(7, stat3.wordCount("type"));
        Assert.assertEquals(655, stat5.wordCount("upon"));
        Assert.assertEquals(6900, stat5.wordCount("of"));
        Assert.assertEquals(547, stat5.wordCount("one"));
        Assert.assertEquals(207, stat5.wordCount("kate"));
        Assert.assertEquals(0, stat5.wordCount("wordnothere"));
        Assert.assertEquals(1, stat6.wordCount("5i"));
        Assert.assertEquals(0, stat6.wordCount(""));
        Assert.assertEquals(2, stat7.wordCount("modifiers"));
        Assert.assertEquals(0, stat7.wordCount("mod"));
        Assert.assertEquals(1, stat7.wordCount("item"));
        Assert.assertEquals(0, stat8.wordCount("item"));
    }

    @Test
    public void testWordPairCount() {
        Assert.assertEquals(0, stat1.wordPairCount("test", ""));
        Assert.assertEquals(0, stat1.wordPairCount("", ""));
        Assert.assertEquals(0, stat2.wordPairCount("", ""));
        Assert.assertEquals(0, stat1.wordPairCount("bit", "byte"));
        Assert.assertEquals(0, stat2.wordPairCount("t", ""));
        Assert.assertEquals(1, stat3.wordPairCount("flash", "cannon"));
        Assert.assertEquals(3, stat3.wordPairCount("sp", "atk"));
        Assert.assertEquals(0, stat3.wordPairCount("atk", "sp"));
        Assert.assertEquals(12, stat3.wordPairCount("the", "following"));
        Assert.assertEquals(4, stat3.wordPairCount("of", "the"));
        Assert.assertEquals(1244, stat5.wordPairCount("of", "the"));
        Assert.assertEquals(201, stat5.wordPairCount("had", "been"));
        Assert.assertEquals(193, stat5.wordPairCount("upon", "the"));
        Assert.assertEquals(0, stat5.wordPairCount("the", "wordnothere"));
        Assert.assertEquals(0, stat6.wordPairCount("5i", ""));
        Assert.assertEquals(0, stat6.wordPairCount("", ""));
        Assert.assertEquals(1, stat7.wordPairCount("item", "modifiers"));
        Assert.assertEquals(1, stat7.wordPairCount("modifiers", "modifiers"));
        Assert.assertEquals(0, stat7.wordPairCount("item", ""));
        Assert.assertEquals(0, stat8.wordPairCount("", "item"));
    }

    @Test
    public void testWordRank() {
        Assert.assertEquals(1, stat1.wordRank("test"));
        Assert.assertEquals(0, stat1.wordRank("file"));
        Assert.assertEquals(0, stat2.wordRank(""));
        Assert.assertEquals(1, stat3.wordRank("the"));
        Assert.assertEquals(2, stat3.wordRank("power"));
        Assert.assertEquals(3, stat3.wordRank("moves"));
        Assert.assertEquals(4, stat3.wordRank("following"));
        Assert.assertEquals(5, stat3.wordRank("and"));
        Assert.assertEquals(6, stat3.wordRank("type"));
        Assert.assertEquals(6, stat3.wordRank("in"));
        Assert.assertEquals(6, stat3.wordRank("a"));
        Assert.assertEquals(6, stat3.wordRank("of"));
        Assert.assertEquals(6, stat3.wordRank("additionally"));
        Assert.assertEquals(6, stat3.wordRank("additionally"));
        Assert.assertEquals(11, stat3.wordRank("sp"));
        Assert.assertEquals(0, stat3.wordRank("wordnothere"));
        Assert.assertEquals(0, stat4.wordRank(""));
        Assert.assertEquals(0, stat5.wordRank(""));
    }

    @Test
    public void testWordPairRank() {
        Assert.assertEquals(0, stat1.wordPairRank("test", ""));
        Assert.assertEquals(0, stat1.wordPairRank("", ""));
        Assert.assertEquals(0, stat2.wordPairRank("", ""));
        Assert.assertEquals(0, stat1.wordPairRank("bit", "byte"));
        Assert.assertEquals(0, stat2.wordPairRank("t", ""));
        Assert.assertEquals(1, stat3.wordPairRank("the", "following"));
        Assert.assertEquals(3, stat3.wordPairRank("in", "power"));
        Assert.assertEquals(4, stat3.wordPairRank("moves", "increase"));
        Assert.assertEquals(4, stat3.wordPairRank("increase", "in"));
        Assert.assertEquals(4, stat3.wordPairRank("and", "sp"));
        Assert.assertEquals(4, stat3.wordPairRank("of", "the"));
        Assert.assertEquals(4, stat3.wordPairRank("a", "random"));
        Assert.assertEquals(9, stat3.wordPairRank("following", "abilities"));
        Assert.assertEquals(1, stat5.wordPairRank("of", "the"));
        Assert.assertEquals(20, stat5.wordPairRank("upon", "the"));
        Assert.assertEquals(0, stat5.wordPairRank("the", "wordnothere"));
        Assert.assertEquals(0, stat6.wordPairRank("5i", ""));
        Assert.assertEquals(0, stat6.wordPairRank("", ""));
        Assert.assertEquals(1, stat7.wordPairRank("item", "modifiers"));
        Assert.assertEquals(1, stat7.wordPairRank("modifiers", "modifiers"));
        Assert.assertEquals(0, stat7.wordPairRank("item", ""));
        Assert.assertEquals(0, stat8.wordPairRank("", "item"));
    }

    @Test
    public void testMostCommonWords() {
        Assert.assertArrayEquals(new String[]{"test"}, stat1.mostCommonWords(1));
        Assert.assertArrayEquals(new String[]{}, stat1.mostCommonWords(0));
        Assert.assertArrayEquals(new String[]{null, null, null, null}, stat2.mostCommonWords(4));
        Assert.assertArrayEquals(new String[]{"the", "power", "moves", "following", "and", "type", "in", "a", "of",
                                              "additionally", "sp"}, stat3.mostCommonWords(11));
        Assert.assertArrayEquals(new String[]{"the", "power"}, stat3.mostCommonWords(2));
        Assert.assertArrayEquals(new String[]{"the", "of", "to", "and", "a", "in", "that", "was", "for", "as",
                                              "it", "his", "which", "he", "this", "by", "had", "with", "but", "is",
                                              "i", "at", "or", "not", "be", "from", "her", "all", "on", "have",
                                              "she", "upon", "so", "were", "one", "an", "their", "been", "would", "no",
                                              "we", "him", "any", "they", "are", "some", "more", "my", "if", "such"},
                                 stat5.mostCommonWords(50));
        Assert.assertArrayEquals(new String[]{"5i"}, stat6.mostCommonWords(1));
        Assert.assertArrayEquals(new String[]{"modifiers", "item"}, stat7.mostCommonWords(2));
        Assert.assertArrayEquals(new String[]{"one", "two", "twotie", "three", "threetie", "threetietie"},
                                 stat9.mostCommonWords(6));
        Assert.assertArrayEquals(new String[]{"one", "two", "twotie", "three", "threetie", "threetietie", null, null, null,
                                              null}, stat9.mostCommonWords(10));
    }

    @Test
    public void testLeastCommonWords() {
        Assert.assertArrayEquals(new String[]{"test"}, stat1.leastCommonWords(1));
        Assert.assertArrayEquals(new String[]{}, stat1.leastCommonWords(0));
        Assert.assertArrayEquals(new String[]{null, null, null, null}, stat2.leastCommonWords(4));
        Assert.assertArrayEquals(new String[]{"sound", "wind", "them", "share", "using", "damage", "extra", "an", "gain",
                                              "longer", "no"}, stat3.leastCommonWords(11));
        Assert.assertArrayEquals(new String[]{"sound", "wind"}, stat3.leastCommonWords(2));
        Assert.assertArrayEquals(new String[]{"newsletter", "includes", "pg", "volunteer", "network", "checks", "donation",
                                              "swamp", "gratefully", "donors", "unsolicited", "solicitation", "paperwork",
                                              "50", "irs", "outdated", "machine", "licensed", "gbnewbypglaforg", "director"},
                                 stat5.leastCommonWords(20));
        Assert.assertArrayEquals(new String[]{"5i"}, stat6.leastCommonWords(1));
        Assert.assertArrayEquals(new String[]{"3521", "item"}, stat7.leastCommonWords(2));
        Assert.assertArrayEquals(new String[]{"threetietie", "threetie", "three", "twotie", "two", "one"},
                                 stat9.leastCommonWords(6));
    }

    @Test
    public void testMostCommonWordPairs() {
        Assert.assertArrayEquals(new String[]{null}, stat1.mostCommonWordPairs(1));
        Assert.assertArrayEquals(new String[]{}, stat1.mostCommonWordPairs(0));
        Assert.assertArrayEquals(new String[]{null, null, null, null}, stat2.mostCommonWordPairs(4));
        Assert.assertArrayEquals(new String[]{"the following", "following moves", "in power", "moves increase", "increase in",
                                              "and sp", "of the", "a random", "following abilities", "additionally boosts",
                                              "sp atk"}, stat3.mostCommonWordPairs(11));
        Assert.assertArrayEquals(new String[]{"the following", "following moves"}, stat3.mostCommonWordPairs(2));
        Assert.assertArrayEquals(new String[]{"of the", "in the", "to the", "and the", "for the", "of a", "from the", "it was",
                                              "to be", "of his", "with the", "on the", "by the", "it is", "in a", "at the",
                                              "had been", "the same", "that the", "upon the"}, stat5.mostCommonWordPairs(20));
        Assert.assertArrayEquals(new String[]{null}, stat6.mostCommonWordPairs(1));
        Assert.assertArrayEquals(new String[]{"item modifiers", "modifiers modifiers"}, stat7.mostCommonWordPairs(2));
        Assert.assertArrayEquals(new String[]{"one two", "two one", "one twotie", "twotie three"}, stat9.mostCommonWordPairs(4));
    }

    @Test
    public void testLeastCommonWordPairs() {
        Assert.assertArrayEquals(new String[]{null}, stat1.leastCommonWordPairs(1));
        Assert.assertArrayEquals(new String[]{}, stat1.leastCommonWordPairs(0));
        Assert.assertArrayEquals(new String[]{null, null, null, null}, stat2.leastCommonWordPairs(4));
        Assert.assertArrayEquals(new String[]{"sound moves", "and sound", "moves and", "wind moves", "them wind", "with them",
                                              "type with", "a type", "share a", "that share", "moves that"},
                                 stat3.leastCommonWordPairs(11));
        Assert.assertArrayEquals(new String[]{"sound moves", "and sound"}, stat3.leastCommonWordPairs(2));
        Assert.assertArrayEquals(new String[]{"about new", "hear about", "newsletter to", "email newsletter", "our email",
                                              "subscribe to", "to subscribe", "ebooks and","our new", "produce our",
                                              "help produce", "foundation how", "make donations", "including how",
                                              "gutenbergtm including", "includes information", "site includes", "this web",
                                              "wwwgutenbergorg this", "facility wwwgutenbergorg"}, stat5.leastCommonWordPairs(20));
        Assert.assertArrayEquals(new String[]{null}, stat6.leastCommonWordPairs(1));
        Assert.assertArrayEquals(new String[]{"modifiers 3521", "modifiers modifiers"}, stat7.leastCommonWordPairs(2));
        Assert.assertArrayEquals(new String[]{"threetie threetietie", "twotie threetie", "three twotie", "twotie three"},
                                 stat9.leastCommonWordPairs(4));
    }

    @Test
    public void testMostCommonCollocs() {
        Assert.assertArrayEquals(new String[]{}, stat1.mostCommonCollocs(0, "fail", -1));
        Assert.assertArrayEquals(new String[]{null}, stat1.mostCommonCollocs(1, "fail", 1));
        Assert.assertArrayEquals(new String[]{null}, stat1.mostCommonCollocs(1, "test", 1));
        Assert.assertArrayEquals(new String[]{}, stat1.mostCommonCollocs(0, "test", 1));
        Assert.assertArrayEquals(new String[]{null, null, null, null}, stat2.mostCommonCollocs(4,"",1));
        Assert.assertArrayEquals(new String[]{"the", "power", "moves", "following", "and"},
                                 stat3.mostCommonCollocs(5, "diamond", -1));
        Assert.assertArrayEquals(new String[]{"the", "power", "moves", "following", "and"},
                                 stat3.mostCommonCollocs(5, "diamond", 1));
        Assert.assertArrayEquals(new String[]{"the", "power"}, stat3.mostCommonCollocs(2, "diamond", -1));
        Assert.assertArrayEquals(new String[]{"moves", "type", "in", "modifiers", "increase"},
                                 stat3.mostCommonCollocs(5, "power", -1));
        Assert.assertArrayEquals(new String[]{"the", "of", "to", "and", "a", "in", "that", "was", "for", "as",
                                              "it", "his", "which", "he", "this", "by", "had", "with", "but", "is",
                                              "i", "at", "or", "not", "be", "from", "her", "all", "on", "have",
                                              "she", "upon", "so", "were", "one", "an", "their", "been", "would", "no",
                                              "we", "him", "any", "they", "are", "some", "more", "my", "if", "such"},
                                 stat5.mostCommonCollocs(50, "director", -1));
    }

    @Test
    public void testMostCommonCollocsExc() {
        Assert.assertArrayEquals(new String[]{}, stat1.mostCommonCollocsExc(0, "fail", -1, new String[]{"e"}));
        Assert.assertArrayEquals(new String[]{null}, stat1.mostCommonCollocsExc(1, "fail", 1, new String[]{"e"}));
        Assert.assertArrayEquals(new String[]{null}, stat1.mostCommonCollocsExc(1, "test", 1, new String[]{"e"}));
        Assert.assertArrayEquals(new String[]{}, stat1.mostCommonCollocsExc(0, "test", 1, new String[]{"e"}));
        Assert.assertArrayEquals(new String[]{null, null, null, null},
                                 stat2.mostCommonCollocsExc(4,"",1, new String[]{"e"}));
        Assert.assertArrayEquals(new String[]{"the", "power", "moves", "following", "and"},
                                 stat3.mostCommonCollocsExc(5, "diamond", -1, new String[]{}));
        Assert.assertArrayEquals(new String[]{"the", "power", "moves", "following", "and"},
                                 stat3.mostCommonCollocsExc(5, "diamond", -1, new String[]{"sdsjndaj"}));
        Assert.assertArrayEquals(new String[]{"the", "following", "and", "type", "in"},
                                 stat3.mostCommonCollocsExc(5, "diamond", -1, new String[]{"power", "moves"}));
        Assert.assertArrayEquals(new String[]{"the", "moves", "following", "and", "type"},
                                 stat3.mostCommonCollocsExc(5, "diamond", 1, new String[]{"power"}));
        Assert.assertArrayEquals(new String[]{"the", "following"},
                                 stat3.mostCommonCollocsExc(2, "diamond", -1, new String[]{"power", "moves"}));
        Assert.assertArrayEquals(new String[]{"type", "modifiers", "dark", "35", "ultra"},
                                 stat3.mostCommonCollocsExc(5, "power", -1, new String[]{"increase", "moves", "in"}));
        Assert.assertArrayEquals(new String[]{"a", "in", "that", "was", "for", "as", "it", "his", "which", "he", "this",
                                              "by", "had", "with", "but", "is", "i", "at", "or", "not", "be", "from", "her",
                                              "all", "on", "have", "she", "upon", "so", "were", "one", "an", "their", "been",
                                              "would", "no", "we", "him", "any", "they", "are", "some", "more", "my", "if",
                                              "such", "you", "could", "very", "there"},
                                 stat5.mostCommonCollocsExc(50, "director", -1, new String[]{"the", "of", "to", "and"}));
    }

    @Test
    public void testGenerateWordString() {
        Assert.assertNull(stat1.generateWordString(0, "fail"));
        Assert.assertNull(stat1.generateWordString(1, "fail"));
        Assert.assertEquals("test", stat1.generateWordString(1, "test"));
        Assert.assertEquals("test", stat1.generateWordString(0, "test"));
        Assert.assertNull(stat2.generateWordString(4,""));
        Assert.assertNull(stat3.generateWordString(5, ""));
        Assert.assertEquals("35 the power moves following and type in a of field upon use", stat3.generateWordString(50, "35"));
        Assert.assertEquals("wind moves and upon", stat3.generateWordString(4, "wind"));
        Assert.assertEquals("some the of", stat5.generateWordString(3, "some"));
    }
}

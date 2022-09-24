import java.util.ArrayList;
import java.util.Collections;

public class WordStat {
    private HashTable individualCounts;
    private HashTable individualRanks;
    private HashTable pairCounts;
    private HashTable pairRanks;
    private ArrayList<String> wordList;
    private ArrayList<HashEntry> individuals;
    private ArrayList<HashEntry> pairs;

    // Computes the statistics from the file name. (Linear time)
    public WordStat(String fileName) {
        wordList = (new Tokenizer(fileName)).wordList();
        individualCounts = new HashTable(wordList.size() * 2);
        pairCounts = new HashTable(wordList.size() * 2);
        calcWordStats();
    }

    // Computes the statistics from the words in the String array. (Linear time)
    public WordStat(String[] words) {
        wordList = (new Tokenizer(words)).wordList();
        individualCounts = new HashTable(wordList.size() * 2);
        pairCounts = new HashTable(wordList.size() * 2);
        calcWordStats();
    }

    // Returns the count of the word argument. Return zero if the word is not in the table. (Constant time)
    public int wordCount(String word) {
        int ret = individualCounts.get(word);
        if (ret == -1) return 0;
        return ret;
    }

    // Returns the count of the word pair w1 w2. Return zero if the word pair is not in the table. (Constant time)
    public int wordPairCount(String w1, String w2) {
        int ret = pairCounts.get(w1 + " " + w2);
        if (ret == -1) return 0;
        return ret;
    }

    // Returns the rank of word, where rank 1 is the most common word. (Constant time)
    public int wordRank(String word) {
        int ret = individualRanks.get(word);
        if (ret == -1) return 0;
        return ret;
    }

    // Returns the rank of the word pair w1 w2 (which should also be normalized) or zero if not in the table. (Constant time)
    public int wordPairRank(String w1, String w2) {
        int ret = pairRanks.get(w1 + " " + w2);
        if (ret == -1) return 0;
        return ret;
    }

    // Returns a String array of the k most common words in decreasing order of their count. (Linear time)
    public String[] mostCommonWords(int k) {
        String[] ret = new String[k];
        for (int i = 0; i < k && i < individuals.size(); i++) {
            ret[i] = individuals.get(i).getKey();
        }
        return ret;
    }

    // Returns a String array of the k least common words in increasing order of their count. (Linear time)
    public String[] leastCommonWords(int k) {
        String[] ret = new String[k];
        for (int i = 0; i < k && i < individuals.size(); i++) {
            ret[i] = individuals.get(individuals.size() - i - 1).getKey();
        }
        return ret;
    }

    // Returns the k most common the word pairs in a String array. (Linear time)
    public String[] mostCommonWordPairs(int k) {
        String[] ret = new String[k];
        for (int i = 0; i < k && i < pairs.size(); i++) {
            ret[i] = pairs.get(i).getKey();
        }
        return ret;
    }

    // Returns the k most common words at a given relative position i to baseWord. (Quadratic time)
    public String[] mostCommonCollocs(int k, String baseWord, int i) {
        String[] ret = new String[k];
        int pos = 0;
        int baseWordIndex = wordList.indexOf(baseWord);
        if (baseWordIndex == -1) return ret; // not found
        baseWordIndex += i;
        // Start from most frequent word, and add to ret if in the right direction relative to baseWord
        for (int x = 0; x < individuals.size() && pos < k; x++) {
            for (int j = baseWordIndex; j >= 0 && j < wordList.size(); j += i) {
                if (wordList.get(j).equals(individuals.get(x).getKey())) { // Valid collocation
                    ret[pos++] = wordList.get(j);
                    break;
                }
            }
        }
        return ret;
    }

    // Returns the k most common words at a given relative position i to baseWord, excluding words in exclusions. (Quadratic time)
    public String[] mostCommonCollocsExc(int k, String baseWord, int i, String[] exclusions) {
        String[] ret = new String[k];
        int pos = 0;
        int baseWordIndex = wordList.indexOf(baseWord);
        if (baseWordIndex == -1) return ret; // not found
        baseWordIndex += i;
        // Start from most frequent word, and add to ret if in the right direction relative to baseWord
        for (int x = 0; x < individuals.size() && pos < k; x++) {
            // Check if individuals[x] is in exclusions and skip check below if so
            boolean excluded = false;
            for (String exclusion : exclusions) {
                if (exclusion.equals(individuals.get(x).getKey())) {
                    excluded = true;
                    break;
                }
            }
            if (!excluded) {
                for (int j = baseWordIndex; j >= 0 && j < wordList.size(); j += i) {
                    if (wordList.get(j).equals(individuals.get(x).getKey())) { // Valid collocation
                        ret[pos++] = wordList.get(j);
                        break;
                    }
                }
            }
        }
        return ret;
    }

    // Returns a string composed of k common words starting with startWord and after each other. (Quadratic time)
    public String generateWordString(int k, String startWord) {
        StringBuilder ret = new StringBuilder(startWord);
        int numEntries = 1;
        int startIndex = wordList.indexOf(startWord);
        if (startIndex++ == -1) return null; // not found
        // Start after most frequent word, and add to ret if it's next most common
        for (int i = 0; i < individuals.size() && numEntries < k; i++) {
            for (int j = startIndex; j < wordList.size(); j++) {
                if (wordList.get(j).equals(individuals.get(i).getKey())) { // Next most common
                    ret.append(" ").append(wordList.get(j));
                    numEntries++;
                    startIndex = j;
                    break;
                }
            }
        }
        return ret.toString();
    }

    // NOVEL METHOD: Returns the k least common the word pairs in a String array
    public String[] leastCommonWordPairs(int k) {
        String[] ret = new String[k];
        for (int i = 0; i < k && i < pairs.size(); i++) {
            ret[i] = pairs.get(pairs.size() - i - 1).getKey();
        }
        return ret;
    }

    // Called from the constructors so code doesn't need to be written twice.
    private void calcWordStats() {
        // Adds individual words (with frequency) to first hash table
        individuals = new ArrayList<HashEntry>();
        for (String word : wordList) {
            individualCounts.put(word, 1);
            if (individualCounts.get(word) == 1) { // Unique word
                individuals.add(new HashEntry(word, 1));
            }
        }
        for (HashEntry entry : individuals) {
            entry.setValue(individualCounts.get(entry.getKey(), entry.getValue()));
        }
        Collections.sort(individuals); // Sort by descending frequency
        // Adds individual words (with rank) to second hash table
        individualRanks = new HashTable();
        int rank = 1;
        for (int i = 0; i < individuals.size(); i++) {
            individualRanks.put(individuals.get(i).getKey(), rank);
            // Don't increment rank if it's the same as the next entry
            if (i + 1 < individuals.size() && individuals.get(i+1).getValue() != individuals.get(i).getValue()) rank = i+2;
        }
        // Adds word pairs (with frequency) to third hash table
        pairs = new ArrayList<HashEntry>();
        for (int i = 0; i < wordList.size() - 1; i++) {
            String word = wordList.get(i) + " " + wordList.get(i+1);
            pairCounts.put(word, 1);
            if (pairCounts.get(word) == 1) pairs.add(new HashEntry(word, 1)); // Unique word pair
        }
        for (HashEntry entry : pairs) {
            entry.setValue(pairCounts.get(entry.getKey(), entry.getValue()));
        }
        Collections.sort(pairs); // Sort by descending frequency
        // Adds word pairs (with rank) to fourth hash table
        pairRanks = new HashTable();
        rank = 1;
        for (int i = 0; i < pairs.size(); i++) {
            pairRanks.put(pairs.get(i).getKey(), rank);
            // Don't increment rank if it's the same as the next entry
            if (i + 1 < pairs.size() && pairs.get(i+1).getValue() != pairs.get(i).getValue()) rank = i+2;
        }
    }

    public static void main(String[] args) {
        System.out.println("Constructing a WordStat object from an array input and normalizing entries.");
        WordStat stat = new WordStat(new String[]{"#--------------------------- 35. Ultra Space [USP] --------------------------------#\n" +
                "\"Cries from distant worlds reverberate...\"",

                ":::TYPE MODIFIERS:::\n" +
                "*\tDark moves increase in power x1.5",

                ":::ABILITY MODIFIERS:::\n" +
                "*\tPokemon with the following abilities randomly become a different type at the end of each turn:\n" +
                "\t\tMultitype\t\tRKS System\n" +
                "*\tVictory Star additionally boosts user and ally's Attack and Sp. Atk x1.5\n" +
                "*\tShadow Shield additionally doubles the Pokemon's Defense and Sp. Def\n" +
                "*\tThe following abilities are disabled:\n" +
                "\t\tMagnet Pull\t\tFlame Body\t\tPlus\t\t\tMinus\t\t\tBlaze\n" +
                "\t\tDownload\t\tForewarn\t\tSolid Rock\t\tParental Bond\t\tSymbiosis\n" +
                "\t\tPower Construct\t\tEarthen Aura\t\tPower Spot\t\tEarthinize\t\tCultivate\n" +
                "\t\tNeutralizing Gas\n" +
                "*\tAir Lock additionally raises Speed upon entry\n" +
                "*\tMold Breaker additionally raises Sp. Atk upon entry\n" +
                "*\tThe stat-changing effects of the following abilities are amplified by 1 stage:\n" +
                "\t\tBeast Boost\t\tSupernova\n" +
                "*\tShields Down additionally boosts Attack and Sp. Atk when HP falls below half",

                ":::MOVE MODIFIERS:::\n" +
                "*\tThe following moves increase in power x1.5:\n" +
                "\t\tPsystrike\t\tAeroblast\t\tSacred Fire\t\tMist Ball\t\tOrigin Pulse\n" +
                "\t\tPrecipice Blades\tDragon Ascent\t\tPsycho Boost\t\tRoar of Time\t\tShadow Force\n" +
                "\t\tSeed Flare\t\tJudgment\t\tV-Create\t\tSacred Sword\t\tSecret Sword\n" +
                "\t\tFusion Bolt\t\tFusion Flare\t\tBolt Strike\t\tBlue Flare\t\tGlaciate\n" +
                "\t\tIce Burn\t\tFreeze Shock\t\tRelic Song\t\tOblivion Wing\t\tThousand Arrows\n" +
                "\t\tThousand Waves\t\tEarth Power\t\tFleur Cannon\t\tPrismatic Laser\t\tSunsteel Strike\n" +
                "\t\tSpectral Thief  \tMoongeist Beam  \tMulti-Attack\t\tContinental Crush\tCore Enforcer\n" +
                "\t\tGenesis Supernova\tSearing Sunraze Smash\tDraco Meteor\t\tAncient Power\t\tMenacing Moonraze Maelstrom\n" +
                "\t\tMeteor Mash\t\tComet Punch\t\tLand's Wrath\t\tDiamond Storm\t\tSoul-Stealing 7-Star Strike\n" +
                "\t\tSwift\t\t\tEnergy Ball\t\tPhoton Geyser\t\tSplintered Stormshards\tLight That Burns the Sky\n" +
                "\t\tMeteor Beam\t\tMeteor Tempest\t\tInfinite Force\n" +
                "*\tThe following moves increase in power x1.2:\n" +
                "\t\tMirror Shot\t\tAurora Beam\t\tSignal Beam\t\tDazzling Gleam\t\tFlash Cannon\n" +
                "\t\tMoonblast\t\tPhoton Geyser\t\tLuster Purge\n" +
                "*\tThe following moves increase in power x2:\n" +
                "\t\tVacuum Wave\t\tSpacial Rend\t\tHyperspace Hole\t\tHyperspace Fury\t\tShattered Psyche\n" +
                "*\tHeart Swap additionally gains Pain Split's effect\n" +
                "*\tThe base Accuracy of the following moves is increased to 100:\n" +
                "\t\tDark Void\n" +
                "* \tLunar Dance additionally boosts all of the recipient's stats\n" +
                "*\tTrick Room, Magic Room and Wonder Room instead last for 8 turns\n" +
                "*\tThe stat-changing effects of the following moves are amplified by 1 stage:\n" +
                "\t\tCosmic Power\t\tFlash\t\t\tCalm Mind\n" +
                "*\tMoonlight instead heals 2/3 max HP\n" +
                "*   \tBlack Hole Eclipse increases in power x4\n" +
                "* \tNature Power becomes Spacial Rend\n" +
                "*\tCamouflage changes the user's type to a random type\n" +
                "*\tSecret Power may lower the target's Sp. Def\n" +
                "*\tTerrain Pulse increases in power x2 and becomes a random type\n" +
                "*\tThe following moves fail upon use:\n" +
                "\t\tEarthquake\t\tFissure\t\t\tDig\t\t\tPoison Gas\t\tSmokescreen\n" +
                "*\tHidden Power instead becomes a random type\n" +
                "*\tVacuum Wave becomes Dark-type\n" +
                "*\tEnergy Ball becomes Psychic-type\n" +
                "*\tLight That Burns the Sky decreases in power x0.5",

                ":::ITEM MODIFIERS:::\n" +
                "*\tMagical Seed boosts Defense and Sp. Def, and creates Wonder Room\n" +
                "*\tThe following items instead boost the power of affected moves x1.5:\n" +
                "\t\tAdamant Orb\t\tGriseous Orb\t\tLustrous Orb",

                ":::FIELD TRANSFORMATION:::\n" +
                "*\tThe following moves transform the field into a Starlight Arena:\n" +
                "\t\tGeomancy\n" +
                "*\tThe following moves transform the field into a random field temporarily:\n" +
                "\t\tHyperspace Hole\t\tTeleport",

                ":::OTHER EFFECTS:::\n" +
                "*\tPrevents all weather (besides Strong Winds) and generated Field Effects\n" +
                "*\tPokemon no longer gain an extra damage boost for using moves that share a type with them\n" +
                "*\tWind moves and Sound moves fail upon use\n" +
                "\n"});
        System.out.println("The word \"pokemon\" appears in the sample " + stat.wordCount("pokemon") + " times.");
        System.out.println("The word pair \"sp atk\" appears in the sample " + stat.wordPairCount("sp", "atk") + " times.");
        System.out.println("The word \"field\" is rank " + stat.wordRank("field") + " most common in the sample.");
        System.out.println("The word pair \"the following\" is rank " + stat.wordPairRank("the", "following") + " most common in the sample.");
        String[] array = stat.mostCommonWords(10);
        StringBuilder builder = new StringBuilder("\"" + array[0]);
        for (int i = 1; i < 9; i++) {
            builder.append("\", \"").append(array[i]);
        }
        builder.append("\", and \"").append(array[9]).append("\"");
        System.out.println("The words " + builder + " are the 10 most common in the sample.");
        array = stat.leastCommonWords(10);
        builder = new StringBuilder("\"" + array[0]);
        for (int i = 1; i < 9; i++) {
            builder.append("\", \"").append(array[i]);
        }
        builder.append("\", and \"").append(array[9]).append("\"");
        System.out.println("The words " + builder + " are the 10 least common in the sample.");
        array = stat.mostCommonWordPairs(10);
        builder = new StringBuilder("\"" + array[0]);
        for (int i = 1; i < 9; i++) {
            builder.append("\", \"").append(array[i]);
        }
        builder.append("\", and \"").append(array[9]).append("\"");
        System.out.println("The word pairs " + builder + " are the 10 most common in the sample.");
        array = stat.leastCommonWordPairs(10);
        builder = new StringBuilder("\"" + array[0]);
        for (int i = 1; i < 9; i++) {
            builder.append("\", \"").append(array[i]);
        }
        builder.append("\", and \"").append(array[9]).append("\"");
        System.out.println("The word pairs " + builder + " are the 10 least common in the sample.");
        array = stat.mostCommonCollocs(10, "geomancy", 1);
        builder = new StringBuilder("\"" + array[0]);
        for (int i = 1; i < 9; i++) {
            builder.append("\", \"").append(array[i]);
        }
        builder.append("\", and \"").append(array[9]).append("\"");
        System.out.println("The words " + builder + " are the 10 most common collocations after \"geomancy\" in the sample.");
        array = stat.mostCommonCollocsExc(10, "geomancy", 1, new String[]{"the", "and", "a"});
        builder = new StringBuilder("\"" + array[0]);
        for (int i = 1; i < 9; i++) {
            builder.append("\", \"").append(array[i]);
        }
        builder.append("\", and \"").append(array[9]).append("\"");
        System.out.println("The words " + builder + " are the 10 most common collocations after \"geomancy\" in the sample " +
                           "(excluding \"and\", \"the\" and \"a\").");
        System.out.println("This is a word string generated by finding \"wind\", the most common word after \"wind\", " +
                           "the most common word after that, and then the most common word " +
                           "after that: \"" + stat.generateWordString(4, "wind") + "\".");
    }
}

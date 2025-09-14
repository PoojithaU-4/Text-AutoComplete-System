import java.io.*;
import java.util.*;

// Node class for Trie
class TrieNode {
    Map<Character, TrieNode> children;
    boolean endOfWord;

    TrieNode() {
        children = new HashMap<>();
        endOfWord = false;
    }
}

// Trie Implementation
class Trie {
    private TrieNode root;
    private Map<String, Integer> frequencyMap; // word -> frequency

    Trie() {
        root = new TrieNode();
        frequencyMap = new HashMap<>();
    }

    // Insert word into Trie + update frequency
    public void insert(String word, int frequency) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
        }
        node.endOfWord = true;

        // Store frequency (update if word already exists)
        frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + frequency);
    }

    // Search for word
    public boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (!node.children.containsKey(c)) return false;
            node = node.children.get(c);
        }
        return node.endOfWord;
    }

    // Autocomplete suggestions (ranked by frequency)
    public List<String> autocomplete(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) return Collections.emptyList();
            node = node.children.get(c);
        }

        List<String> results = new ArrayList<>();
        collectWords(node, prefix, results);

        // Sort by frequency (high → low)
        results.sort((a, b) -> frequencyMap.get(b) - frequencyMap.get(a));
        return results;
    }

    // DFS to collect words
    private void collectWords(TrieNode node, String prefix, List<String> results) {
        if (node.endOfWord) {
            results.add(prefix);
        }
        for (char c : node.children.keySet()) {
            collectWords(node.children.get(c), prefix + c, results);
        }
    }
}

// Main Driver
public class Main {
    public static void main(String[] args) {
        Trie trie = new Trie();

        // Load words from dictionary file
        try (BufferedReader br = new BufferedReader(new FileReader("dictionary.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    String word = parts[0].toLowerCase();
                    int freq = Integer.parseInt(parts[1]);
                    trie.insert(word, freq);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading dictionary file: " + e.getMessage());
        }

        // Interactive Console
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("\nEnter prefix (or 'exit' to quit): ");
            String prefix = sc.nextLine().toLowerCase();
            if (prefix.equals("exit")) break;

            List<String> suggestions = trie.autocomplete(prefix);

            if (suggestions.isEmpty()) {
                System.out.println("No suggestions found.");
            } else {
                System.out.println("Suggestions (ranked): " + suggestions);
            }
        }
        sc.close();
    }
}

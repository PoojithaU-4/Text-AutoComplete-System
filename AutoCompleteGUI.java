import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.io.*;

// Trie Node
class TrieNode {
    Map<Character, TrieNode> children;
    boolean endOfWord;

    TrieNode() {
        children = new HashMap<>();
        endOfWord = false;
    }
}

// Trie with frequency ranking
class Trie {
    private TrieNode root;
    private Map<String, Integer> frequencyMap;

    Trie() {
        root = new TrieNode();
        frequencyMap = new HashMap<>();
    }

    // Insert word with frequency
    public void insert(String word, int frequency) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
        }
        node.endOfWord = true;

        frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + frequency);
    }

    // Autocomplete with ranking
    public List<String> autocomplete(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) return Collections.emptyList();
            node = node.children.get(c);
        }

        List<String> results = new ArrayList<>();
        collectWords(node, prefix, results);

        // Sort by frequency (descending)
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

// GUI Class
public class AutoCompleteGUI extends JFrame {
    private Trie trie;
    private JTextField searchField;
    private DefaultListModel<String> listModel;
    private JList<String> suggestionList;

    public AutoCompleteGUI() {
        trie = new Trie();
        loadDictionary("dictionary.txt");

        setTitle("Autocomplete System (Trie)");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Search field
        searchField = new JTextField();
        add(searchField, BorderLayout.NORTH);

        // Suggestion area
        listModel = new DefaultListModel<>();
        suggestionList = new JList<>(listModel);
        add(new JScrollPane(suggestionList), BorderLayout.CENTER);

        // Event: update suggestions while typing
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updateSuggestions(); }
            public void removeUpdate(DocumentEvent e) { updateSuggestions(); }
            public void changedUpdate(DocumentEvent e) { updateSuggestions(); }
        });
    }

    // Load words from dictionary file
    private void loadDictionary(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
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
            JOptionPane.showMessageDialog(this, "Error loading dictionary: " + e.getMessage());
        }
    }

    // Update suggestions list
    private void updateSuggestions() {
        String prefix = searchField.getText().toLowerCase();
        listModel.clear();
        if (!prefix.isEmpty()) {
            List<String> suggestions = trie.autocomplete(prefix);
            for (String s : suggestions) {
                listModel.addElement(s + " (freq: " + trie.autocomplete(s).size() + ")");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AutoCompleteGUI gui = new AutoCompleteGUI();
            gui.setVisible(true);
        });
    }
}

### **Text Autocomplete System (Java + Trie + Swing GUI)**

A Java-based text autocomplete system that uses the Trie data structure for fast word suggestions.

The project includes a simple Swing GUI where users can type text and get real-time suggestions.



###### Features

* Loads dictionary words from `dictionary.txt`
* Fast prefix search using Trie
* Real-time suggestions as you type
* GUI built with Swing
* Easily expandable dictionary (add more words to `dictionary.txt`)



###### Project Structure

Text-AutoComplete-System/

&nbsp;  src/ # Java source files

&nbsp;    AutoCompleteGUI.java

&nbsp;    Main.java

&nbsp;    Trie.java

&nbsp;    TrieNode.java

&nbsp;  resources/ # External files

&nbsp;    dictionary.txt

&nbsp;    .gitignore

&nbsp;    README.md

###### 

###### How to Run

1\. Clone the repo:

git clone https://github.com//Text-AutoComplete-System.git

cd Text-AutoComplete-System

2\. Compile:

javac src/\*.java

3\. Run:

java src.Main

4\. Add more words to `resources/dictionary.txt` (one per line).



**Example:**

If dictionary.txt contains:

apple

application

apply

banana

band

bandwidth

Typing 'app' suggests:

apple

application

apply



###### Future Enhancements

* Rank suggestions by frequency/popularity
* Add search history feature
* Modern GUI (JavaFX)
* Support for large dictionaries (50k+ words)



###### Tech Stack

* Java (Core, Collections, Swing)
* Trie Data Structure
* Swing GUI
* BufferedReader for dictionary loading



**Author**

Poojitha Udutha

Passionate about problem-solving, AI/ML, and building practical software solutions.

LinkedIn: https://www.linkedin.com

GitHub: https://github.com/PoojithaU-4


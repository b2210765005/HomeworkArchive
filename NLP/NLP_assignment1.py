import re
import codecs

def initialVocabulary():
    # You can use this function to create the initial vocabulary.

    return list("abcçdefgğhıijklmnoöprsştuüvyzwxq" +
                "ABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVYZWXQ" +
                "0123456789" + " " +
                "!'^#+$%&/{([)]=}*?\\_-<>|.:´,;`@€¨~\"é")


def tokenize_corpus(corpus):

    # Example: "kas" -> [' ', 'k', 'a', 's', '_']

    words = re.split(r'\s+', corpus.strip())
    return [list(" " + word + "_") for word in words]


def count_bigrams(tokenized_corpus):

   # Counts the frequencies of two consecutive tokens (bigrams) for each word in the TokenizedCorpus.

    bigram_counts = {}
    for word in tokenized_corpus:
        for i in range(len(word) - 1):
            bigram = (word[i], word[i + 1])
            bigram_counts[bigram] = bigram_counts.get(bigram, 0) + 1
    return bigram_counts

def merge_bigram_in_word(word, bigram):

    # In the token list, it creates a new token by combining the places where the given bigram occurs, the left and right tokens.

    new_word = []
    i = 0
    while i < len(word):
        if i < len(word) - 1 and (word[i], word[i + 1]) == bigram:
            new_word.append(word[i] + word[i + 1])
            i += 2
        else:
            new_word.append(word[i])
            i += 1
    return new_word


def bpeCorpus(corpus, maxMergeCount=10):

    tokenized_corpus = tokenize_corpus(corpus)
    merges = []
    learned_tokens = []
    vocab = initialVocabulary()[:]

    for _ in range(maxMergeCount):
        bigram_counts = count_bigrams(tokenized_corpus)
        if not bigram_counts:
            break

        # Frequency bigram, in case of equality choose the smallest alphabetically
        sorted_bigrams = sorted(bigram_counts.items(), key=lambda x: (-x[1], x[0]))
        best_bigram, count = sorted_bigrams[0]
        if count == 0:
            break
        merges.append((best_bigram, count))
        new_token = best_bigram[0] + best_bigram[1]
        learned_tokens.append(new_token)
        tokenized_corpus = [merge_bigram_in_word(word, best_bigram) for word in tokenized_corpus]

    # Add learned tokens to the initial vocabulary
    vocabulary = vocab + learned_tokens
    return merges, vocabulary, tokenized_corpus


def bpeFN(fileName, maxMergeCount=10):

    with codecs.open(fileName, 'r', 'utf-8') as f:
        corpus = f.read()
    return bpeCorpus(corpus, maxMergeCount)


def bpeTokenize(str, merges):

    tokenized_words = tokenize_corpus(str)
    # Applying all learned merge operations in order
    for merge_entry in merges:
        bigram = merge_entry[0]
        tokenized_words = [merge_bigram_in_word(word, bigram) for word in tokenized_words]
    return tokenized_words


def bpeFNToFile(infn, maxMergeCount=10, outfn="output.txt"):
    # Please don't change this function.
    # After completing all the functions above, call this function with the sample input "hw01_bilgisayar.txt".
    # The content of your output files must match the sample outputs exactly.
    # You can refer to "Example Output Files" section in the assignment document for more details.

    (Merges, Vocabulary, TokenizedCorpus) = bpeFN(infn, maxMergeCount)
    outfile = open(outfn, "w", encoding='utf-8')
    outfile.write("Merges:\n")
    outfile.write(str(Merges))
    outfile.write("\n\nVocabulary:\n")
    outfile.write(str(Vocabulary))
    outfile.write("\n\nTokenizedCorpus:\n")
    outfile.write(str(TokenizedCorpus))
    outfile.close()


if __name__ == "__main__":
    bpeFNToFile("hw01_bilgisayar.txt", maxMergeCount=200, outfn="output.txt")

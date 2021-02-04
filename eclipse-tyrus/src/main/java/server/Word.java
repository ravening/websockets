package server;

public class Word {
    private final String scrambledWord;
    private final String unscrambleWord;

    Word(String scrambledWord, String unscrambleWord) {
        this.scrambledWord = scrambledWord;
        this.unscrambleWord = unscrambleWord;
    }

    public String getScrambledWord() {
        return scrambledWord;
    }

    public String getUnscrambleWord() {
        return unscrambleWord;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((scrambledWord == null) ? 0 : scrambledWord.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Word other = (Word) obj;
        if (scrambledWord == null) {
            if (other.scrambledWord != null)
                return false;
        } else if (!scrambledWord.equals(other.scrambledWord))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Word [scrambbledWord=" + scrambledWord + ", unscrambbledWord=" + unscrambleWord + "]";
    }
}

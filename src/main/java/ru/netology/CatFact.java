package ru.netology;

import org.jetbrains.annotations.NotNull;

import static ru.netology.AnimalsType.CAT;
import static ru.netology.AnimalsType.NOT_ANIMAL;

public class CatFact {

    private String id;
    private String text;
    private AnimalsType type;
    private String user;
    private int upvotes;

    public CatFact(String @NotNull [] attr) {
        this.id = attr[0];
        this.text = attr[1];
        this.type = (attr[2].equalsIgnoreCase("cat")) ? CAT : NOT_ANIMAL;
        this.user = attr[3];
        this.upvotes = (attr[4] != null) ? Integer.parseInt(attr[4]) : 0;
    }

    public int getUpvotes() {
        return this.upvotes;
    }

    public String toString() {
        return "Fact about Cat {id=" + this.id
                + ", \ntext='" + this.text + "'"
                + ", \ntype='" + this.type + "'"
                + ", \nuser='" + this.user + "'"
                + ", \nupvotes=" + this.upvotes + "}\n";
    }


}

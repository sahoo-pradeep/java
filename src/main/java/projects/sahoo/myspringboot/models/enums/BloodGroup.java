package projects.sahoo.myspringboot.models.enums;

import lombok.Getter;

@Getter
public enum BloodGroup {
    A_POSITIVE("A+"),
    A_NEGATIVE("A-"),
    B_POSITIVE("B+"),
    B_NEGATIVE("B-"),
    AB_POSITIVE("AB+"),
    AB_NEGATIVE("AB-"),
    O_POSITIVE("O+"),
    O_NEGATIVE("O-");

    String notation;

    BloodGroup(String notation) {
        this.notation = notation;
    }

    public String getNotation() {
        return notation;
    }
}

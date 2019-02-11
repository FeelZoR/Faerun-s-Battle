package me.feelzor.faerunbattle.warriors;

public enum WarriorType {
    DWARF("Dwarf"),
    ELF("Elf"),
    DWARF_LEADER("Dwarf Leader"),
    ELF_LEADER("Elf Leader"),
    PALADIN("Paladin"),
    RECRUITER("Recruiter"),
    HEALER("Healer");

    String name;
    WarriorType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

package nl.jeltej.training.reflection;

class Person {
    private String name;
    private static int numPeople = 0;

    public Person(String name) {
        this.name = name;
        numPeople++;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static void printPerson(Person person) {
        System.out.println(person.getName());
    }

}

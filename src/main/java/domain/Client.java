package domain;

import java.util.ArrayList;
import java.util.Objects;

public class Client extends BaseEntity<Long> {
    String name;

    public Client(String name) {
        this.name = name;
    }

    // -- GETTERS --
    public String getName() {
        return name;
    }

    // -- SETTERS --
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        return Objects.equals(this.name, client.getName());
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                '}';
    }
}

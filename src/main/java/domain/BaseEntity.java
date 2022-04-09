package domain;


public class BaseEntity<ID> {
    private ID id;

    /**
     * @return id-ul unui obiect
     */
    public ID getId() {
        return id;
    }

    /**
     * Modifica id-ul unul obiect
     *
     * @param id - noul id
     */
    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }
}

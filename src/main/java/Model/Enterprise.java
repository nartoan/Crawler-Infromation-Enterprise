package Model;
/**
 * Created by DucToan on 14/03/2017.
 */
public class Enterprise {
    private String taxCode;
    private String name;
    private String timeUpdate;
    private String typeOfTax;

    public Enterprise() {
        taxCode = "";
        name = "";
        timeUpdate = null;
        typeOfTax = "";
    }

    public Enterprise(String taxCode, String name, String timeUpdate, String typeOfTax) {
        this.taxCode = taxCode;
        this.name = name;
        this.timeUpdate = timeUpdate;
        this.typeOfTax = typeOfTax;
    }

    @Override
    public String toString() {
        return "taxCode :" + taxCode + "\n" +
                "name : " + name + "\n" +
                "timeUpdate :" + timeUpdate +"\n" +
                "typeOfTax : " + typeOfTax + "\n" +
                "\n";
    }

    public String getTypeOfTax() {
        return typeOfTax;
    }

    public void setTypeOfTax(String typeOfTax) {
        this.typeOfTax = typeOfTax;
    }

    public String getTimeUpdate() {
        return timeUpdate;
    }

    public void setTimeUpdate(String timeUpdate) {
        this.timeUpdate = timeUpdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }
}

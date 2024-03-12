package solution2;

class Fine {
    public String first_name;
    public String last_name;
    public String type;
    public Integer fine_amount;
    public String date_time;

    public Fine (String first_name, String last_name, String type, Integer fine_amount, String date_time) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.type = type;
        this.fine_amount = fine_amount;
        this.date_time = date_time;
    }

    @Override
    public String toString() {
        return "{ " + "first_name: " + first_name + ", last_name: " + last_name + ", type: " + type + ", fine_amount: " + fine_amount + ", date_time: " + date_time + " }";
    }
}

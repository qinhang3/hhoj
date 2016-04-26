
class Test{
    public static void main(String[] args){
        System.out.println(gete().getMessage());
    }
    public static Exception gete(){
        return new Exception("asdfasfda");
    }
}

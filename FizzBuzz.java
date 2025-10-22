public class FizzBuzz {
    public void fizzBuzz(int num){
        for(int i = 1; i<=num; i++){
            if(i % 3 == 0 && i % 5 ==0){
                System.out.println("fizzbuzz");
            }
            else if(i % 3 == 0){
                System.out.println("fizz");
            }
            else if (i % 5 == 0){
                System.out.println("buzz");
            }
            else{
                System.out.println(i);
            }
        }
    }
    public static void main(String[] args) {
        FizzBuzz fizzBuzz = new FizzBuzz();
        fizzBuzz.fizzBuzz(15);
    }
}

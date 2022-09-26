package org.example;

public class User {
    private String password;
    public void initPassword(PasswordGenerator passwordGenerator){
        // 알수 없는 부분을 주입
//        RandomPasswordGenerator randomPasswordGenerator = new RandomPasswordGenerator();
//        String randomPassword = randomPasswordGenerator.generatePassword();
        String password = passwordGenerator.generatePassword();
        if(password.length() >= 8 && password.length()<=12){
            this.password = password;
        }
    }

    public String getPassword() {
        return password;
    }
}

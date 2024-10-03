package kr.co.kim.service;

import kr.co.kim.model.User;

public class UserService implements IUserService {

    @Override
    public User createUser(String params) {
        String[] inputData = params.split("&");
        User user = new User();
        for (int idx = 0; idx < inputData.length; idx++) {
            String[] keyValue = inputData[idx].split("=");
            if (keyValue.length < 2)
                continue;

            switch (keyValue[0]) {
                case "id":
                    user.setId(keyValue[1]);
                    break;
                case "password":
                    user.setPassword(keyValue[1]);
                    break;
                case "name":
                    user.setName(keyValue[1]);
                    break;
                case "email":
                    user.setEmail(keyValue[1]);
                    break;
                default:
                    break;
            }
        }

        if (!user.vailidateInputDate()) {
            return null;
        }

        return user;
    }

}

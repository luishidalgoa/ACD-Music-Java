package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Interfaces.iUserDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.User;

import java.util.Set;

public class UserDAO implements iUserDAO {
    @Override
    public UserDTO addUser(User user) {
        return null;
    }

    @Override
    public boolean delete(int idUser) {
        return false;
    }

    @Override
    public UserDTO searchByEmail(String email) {
        return null;
    }

    @Override
    public UserDTO searchByNickname(String nickname) {
        return null;
    }

    @Override
    public Set<UserDTO> searchByName(String filterWord) {
        return null;
    }
}

package org.skillswap.skillswapbackend.Models;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Admin extends Personne {
    public Admin() {
        setRole(Role.ADMIN);
    }
}

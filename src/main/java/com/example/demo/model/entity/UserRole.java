package com.example.demo.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "demo_user_role")
public class UserRole extends _BaseEntity {

    @Getter
    @Setter
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserRoleId implements Serializable {
        private Long userId;
        private Long roleId;
    }

    @EmbeddedId private UserRoleId id = new UserRoleId();

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    public void setUser(User user) {
        this.user = user;
        this.id.userId = user.getId();
    }

    public void setRole(Role role) {
        this.role = role;
        this.id.roleId = role.getId();
    }
}

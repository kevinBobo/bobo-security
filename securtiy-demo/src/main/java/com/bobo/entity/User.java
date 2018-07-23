package com.bobo.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author bobo
 * @Description:
 * @date 2018/7/20下午2:24
 */
@Setter
@Entity
public class User implements Serializable {


    private static final long serialVersionUID = -8094161668261403212L;

    public interface UserSimpleView{}
    public interface UserDetialView extends UserSimpleView{}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;
    private String password;

    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    @JsonView(UserDetialView.class)
    public String getPassword() {
        return password;
    }

    public long getId() {
        return id;
    }
}

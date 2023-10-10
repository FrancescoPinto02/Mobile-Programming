package com.example.rubrica;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.Objects;

public class Contact implements Comparable<Contact>, Serializable {
    private String name;
    private String phoneNumber;
    private Drawable picture;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.picture = null;
    }

    public Contact(String name, String phoneNumber, Context context) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.picture = context.getResources().getDrawable(R.drawable.faceplaceholder);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Drawable getPicture() {
        return picture;
    }

    public void setPicture(Drawable picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(name, contact.name) && Objects.equals(phoneNumber, contact.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber);
    }

    @Override
    public String toString() {
        return name + ": " + phoneNumber;
    }

    @Override
    public int compareTo(Contact contact) {
        return this.name.compareTo(contact.name);
    }
}

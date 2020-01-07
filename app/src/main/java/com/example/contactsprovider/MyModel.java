package com.example.contactsprovider;
 class MyModel {
    String name;
    String number;
    String imageUri;

    public MyModel(String name, String number, String imageUri) {
        this.name = name;
        this.number = number;
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getImageUri() {
        return imageUri;
    }
}



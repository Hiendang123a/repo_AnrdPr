package com.example.society.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String ward;   // Phường/Xã
    private String district; // Quận/Huyện/Thành phố trực thuộc tỉnh
    private String province; // Tỉnh/Thành phố trực thuộc Trung ương
}
package io.github.cepr0.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Model {
	private int id;
	@JsonIgnore private int version;
	private String name;
}

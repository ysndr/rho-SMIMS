package game;

import com.google.gson.annotations.SerializedName;

public enum SpielStatus {
	@SerializedName("nichtAngegeben") nichtAngegeben,
	@SerializedName("Vorbereitung") Vorbereitung,
	@SerializedName("Versorgung") Versorgung,
	@SerializedName("Versorgung") Angriff,
	@SerializedName("Versorgung") Truppenbewegung,
}

package com.mx.org.concentradora.model;

import java.util.Date;

public class Log {

	private int id;
	private String tclave;
	private String caja;
	private int numTicket;
	private Date atmfecha;
	private Double atmventa;
	private int iclave;
	private String icb;
	private int pclave;
	private String respPt;
	private String numRef;
	private long numConf;
	private long numUnicoPt;
	private int reversada;
	private int userId;
	private String stAV;
	private String stED;
	private String stRD;
	private String stGE;
	private String stGD;
	private String stUV;
	private String stUE;
	private String stUD;
	private String stFP;
	private String stIT;
	private String stCP;
	private Date fecha;
	private int encId;
	private int servicios;
	private int idturno;

	public Log(String tclave, String caja, int id, int numTicket, Date atmfecha, Double atmventa, int iclave,
			String icb, int pclave, String respPt, String numRef, long numConf, long numUnicoPt, int reversada,
			int userId, String stAV, String stED, String stRD, String stGE, String stGD, String stUV, String stUE,
			String stUD, String stFP, String stIT, String stCP, Date fecha, int encId, int servicios, int idturno) {
		super();
		this.tclave = tclave;
		this.caja = caja;
		this.id = id;
		this.numTicket = numTicket;
		this.atmfecha = atmfecha;
		this.atmventa = atmventa;
		this.iclave = iclave;
		this.icb = icb;
		this.pclave = pclave;
		this.respPt = respPt;
		this.numRef = numRef;
		this.numConf = numConf;
		this.numUnicoPt = numUnicoPt;
		this.reversada = reversada;
		this.userId = userId;
		this.stAV = stAV;
		this.stED = stED;
		this.stRD = stRD;
		this.stGE = stGE;
		this.stGD = stGD;
		this.stUV = stUV;
		this.stUE = stUE;
		this.stUD = stUD;
		this.stFP = stFP;
		this.stIT = stIT;
		this.stCP = stCP;
		this.fecha = fecha;
		this.encId = encId;
		this.servicios = servicios;
		this.idturno = idturno;
	}

	public Log() {

	}

	public String getTclave() {
		return tclave;
	}

	public void setTclave(String tclave) {
		this.tclave = tclave;
	}

	public String getCaja() {
		return caja;
	}

	public void setCaja(String caja) {
		this.caja = caja;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumTicket() {
		return numTicket;
	}

	public void setNumTicket(int numTicket) {
		this.numTicket = numTicket;
	}

	public Date getAtmfecha() {
		return atmfecha;
	}

	public void setAtmfecha(Date atmfecha) {
		this.atmfecha = atmfecha;
	}

	public Double getAtmventa() {
		return atmventa;
	}

	public void setAtmventa(Double atmventa) {
		this.atmventa = atmventa;
	}

	public int getIclave() {
		return iclave;
	}

	public void setIclave(int iclave) {
		this.iclave = iclave;
	}

	public String getIcb() {
		return icb;
	}

	public void setIcb(String icb) {
		this.icb = icb;
	}

	public int getPclave() {
		return pclave;
	}

	public void setPclave(int pclave) {
		this.pclave = pclave;
	}

	public String getRespPt() {
		return respPt;
	}

	public void setRespPt(String respPt) {
		this.respPt = respPt;
	}

	public String getNumRef() {
		return numRef;
	}

	public void setNumRef(String numRef) {
		this.numRef = numRef;
	}

	public long getNumConf() {
		return numConf;
	}

	public void setNumConf(long numConf) {
		this.numConf = numConf;
	}

	public long getNumUnicoPt() {
		return numUnicoPt;
	}

	public void setNumUnicoPt(long numUnicoPt) {
		this.numUnicoPt = numUnicoPt;
	}

	public int getReversada() {
		return reversada;
	}

	public void setReversada(int reversada) {
		this.reversada = reversada;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getStAV() {
		return stAV;
	}

	public void setStAV(String stAV) {
		this.stAV = stAV;
	}

	public String getStED() {
		return stED;
	}

	public void setStED(String stED) {
		this.stED = stED;
	}

	public String getStRD() {
		return stRD;
	}

	public void setStRD(String stRD) {
		this.stRD = stRD;
	}

	public String getStGE() {
		return stGE;
	}

	public void setStGE(String stGE) {
		this.stGE = stGE;
	}

	public String getStGD() {
		return stGD;
	}

	public void setStGD(String stGD) {
		this.stGD = stGD;
	}

	public String getStUV() {
		return stUV;
	}

	public void setStUV(String stUV) {
		this.stUV = stUV;
	}

	public String getStUE() {
		return stUE;
	}

	public void setStUE(String stUE) {
		this.stUE = stUE;
	}

	public String getStUD() {
		return stUD;
	}

	public void setStUD(String stUD) {
		this.stUD = stUD;
	}

	public String getStFP() {
		return stFP;
	}

	public void setStFP(String stFP) {
		this.stFP = stFP;
	}

	public String getStIT() {
		return stIT;
	}

	public void setStIT(String stIT) {
		this.stIT = stIT;
	}

	public String getStCP() {
		return stCP;
	}

	public void setStCP(String stCP) {
		this.stCP = stCP;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getEncId() {
		return encId;
	}

	public void setEncId(int encId) {
		this.encId = encId;
	}

	public int getServicios() {
		return servicios;
	}

	public void setServicios(int servicios) {
		this.servicios = servicios;
	}

	public int getIdturno() {
		return idturno;
	}

	public void setIdturno(int idturno) {
		this.idturno = idturno;
	}

}

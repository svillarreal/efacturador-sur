package ar.com.proevolutionit.efacturador.base.domain.dto;

import java.io.Serializable;

/**
 * Clase que es utilizada por {@link RangeField} para devolver un rango de datos
 * 
 * @author lscardanzan
 * 
 * @param <T>
 */
public class RangeType<T> implements Serializable {

	private T from;

	private T to;

	public RangeType() {
		super();
	}

	public RangeType(T from, T to) {
		this();
		this.from = from;
		this.to = to;
	}

	public T getFrom() {
		return from;
	}

	public void setFrom(T from) {
		this.from = from;
	}

	public T getTo() {
		return to;
	}

	public void setTo(T to) {
		this.to = to;
	}

	public boolean isEmpty() {
		return this.from == null && this.to == null;
	}

}
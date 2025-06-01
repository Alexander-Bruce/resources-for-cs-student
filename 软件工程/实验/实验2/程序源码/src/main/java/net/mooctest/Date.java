package net.mooctest;

import java.util.Objects;

public class Date {
	private Day d;
	private Month m;
	private Year y;

	public Date(int pMonth, int pDay, int pYear) {
		y = new Year(pYear);
		m = new Month(pMonth, y);
		d = new Day(pDay, m);
	}

	public void increment() {
		if (!d.increment()) {
			if (!m.increment()) {
				y.increment();
				m.setMonth(1, y);
			}
			d.setDay(1, m);
		}
	}

	public void printDate() {
		System.out.println(m.getMonth() + "/" + d.getDay() + "/" + y.getYear());
	}

	public Day getDay() {
		return d;
	}

	public Month getMonth() {
		return m;
	}

	public Year getYear() {
		return y;
	}

	public boolean equals(Object o) {
		if (o instanceof Date) {
			if (this.y.equals(((Date) o).y) && this.m.equals(((Date) o).m)
					&& this.d.equals(((Date) o).d))
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		// Hash based on the fields used in equals()
		return Objects.hash(d, m, y);
	}

	public String toString() {
		return (m.getMonth() + "/" + d.getDay() + "/" + y.getYear());
	}
}

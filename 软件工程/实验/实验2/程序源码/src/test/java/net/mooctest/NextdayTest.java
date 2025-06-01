package net.mooctest;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class NextdayTest {

	// 辅助方法：创建预期日期，不经过 nextDay 逻辑
	private Date createDate(int m, int d, int y) {
		return new Date(m, d, y);
	}

	// --- nextDay 逻辑的测试用例 ---

	// 测试用例 1: 月内简单的日期递增
	@Test
	public void testSimpleIncrement() {
		Date inputDate = new Date(10, 15, 2023); // 2023年10月15日
		Date expectedDate = createDate(10, 16, 2023); // 2023年10月16日
		assertEquals(expectedDate, Nextday.nextDay(inputDate));
		// MC/DC 覆盖:
		// Date.increment: !d.increment() -> false
		// Day.increment: currentPos <= m.getMonthSize() -> true (15 <= 31)
	}

	// 测试用例 2: 31天月份的月末
	@Test
	public void testEndOf31DayMonth() {
		Date inputDate = new Date(1, 31, 2023); // 2023年1月31日
		Date expectedDate = createDate(2, 1, 2023); // 2023年2月1日
		assertEquals(expectedDate, Nextday.nextDay(inputDate));
		// MC/DC 覆盖:
		// Date.increment: !d.increment() -> true
		// Day.increment: currentPos <= m.getMonthSize() -> false (32 > 31)
		// Date.increment: !m.increment() -> false
		// Month.increment: currentPos > 12 -> false (2 <= 12)
	}

	// 测试用例 3: 30天月份的月末
	@Test
	public void testEndOf30DayMonth() {
		Date inputDate = new Date(4, 30, 2023); // 2023年4月30日
		Date expectedDate = createDate(5, 1, 2023); // 2023年5月1日
		assertEquals(expectedDate, Nextday.nextDay(inputDate));
		// MC/DC 覆盖: (与 testEndOf31DayMonth 类似，月份天数不同)
		// Day.increment: currentPos <= m.getMonthSize() -> false (31 > 30)
	}

	// 测试用例 4: 非闰年2月末
	@Test
	public void testEndOfFebruaryNonLeap() {
		Date inputDate = new Date(2, 28, 2023); // 2023年2月28日 (非闰年)
		Date expectedDate = createDate(3, 1, 2023); // 2023年3月1日
		assertEquals(expectedDate, Nextday.nextDay(inputDate));
		// MC/DC 覆盖:
		// Month.getMonthSize: y.isLeap() -> false (对于 2023年)
		// Day.increment: currentPos <= m.getMonthSize() -> false (29 > 28)
		// Year.isLeap: (覆盖非闰年正年份的几个条件)
		//   currentPos >= 0 -> true
		//   currentPos % 4 == 0 -> false (2023 % 4 != 0)
	}

	// 测试用例 5: 闰年2月末 (能被4整除，不能被100整除)
	@Test
	public void testEndOfFebruaryLeap_DivBy4() {
		Date inputDate = new Date(2, 29, 2024); // 2024年2月29日 (闰年)
		Date expectedDate = createDate(3, 1, 2024); // 2024年3月1日
		assertEquals(expectedDate, Nextday.nextDay(inputDate));
		// MC/DC 覆盖:
		// Month.getMonthSize: y.isLeap() -> true (对于 2024年)
		// Day.increment: currentPos <= m.getMonthSize() -> false (30 > 29)
		// Year.isLeap: (覆盖闰年 /4 的条件)
		//   currentPos >= 0 -> true
		//   currentPos % 4 == 0 -> true (2024 % 4 == 0)
		//   currentPos % 100 != 0 -> true (2024 % 100 != 0)
		//   -> 判定为 true
	}

	// 测试用例 6: 闰年2月中 (能被4整除，不能被100整除) - 日期递增正常
	@Test
	public void testMiddleOfFebruaryLeap_DivBy4() {
		Date inputDate = new Date(2, 15, 2024); // 2024年2月15日 (闰年)
		Date expectedDate = createDate(2, 16, 2024); // 2024年2月16日
		assertEquals(expectedDate, Nextday.nextDay(inputDate));
		// MC/DC 覆盖:
		// Month.getMonthSize: y.isLeap() -> true (对于 2024年)
		// Day.increment: currentPos <= m.getMonthSize() -> true (16 <= 29)
		// Year.isLeap: (条件已在之前的测试中覆盖)
	}


	// 测试用例 7: 非闰年2月末 (能被100整除，不能被400整除)
	@Test
	public void testEndOfFebruaryNonLeap_DivBy100() {
		Date inputDate = new Date(2, 28, 2100); // 2100年2月28日 (非闰年)
		Date expectedDate = createDate(3, 1, 2100); // 2100年3月1日
		assertEquals(expectedDate, Nextday.nextDay(inputDate));
		// MC/DC 覆盖:
		// Month.getMonthSize: y.isLeap() -> false (对于 2100年)
		// Day.increment: currentPos <= m.getMonthSize() -> false (29 > 28)
		// Year.isLeap: (覆盖非闰年 /100 的条件)
		//   currentPos >= 0 -> true
		//   currentPos % 4 == 0 -> true (2100 % 4 == 0)
		//   currentPos % 100 != 0 -> false (2100 % 100 == 0)
		//   currentPos % 400 == 0 -> false (2100 % 400 != 0)
		//   -> 判定为 false
	}

	// 测试用例 8: 闰年2月末 (能被400整除)
	@Test
	public void testEndOfFebruaryLeap_DivBy400() {
		Date inputDate = new Date(2, 29, 2000); // 2000年2月29日 (闰年)
		Date expectedDate = createDate(3, 1, 2000); // 2000年3月1日
		assertEquals(expectedDate, Nextday.nextDay(inputDate));
		// MC/DC 覆盖:
		// Month.getMonthSize: y.isLeap() -> true (对于 2000年)
		// Day.increment: currentPos <= m.getMonthSize() -> false (30 > 29)
		// Year.isLeap: (覆盖闰年 /400 的条件)
		//   currentPos >= 0 -> true
		//   currentPos % 4 == 0 -> true (2000 % 4 == 0)
		//   currentPos % 100 != 0 -> false (2000 % 100 == 0)
		//   currentPos % 400 == 0 -> true (2000 % 400 == 0)
		//   -> 判定为 true
	}

	// 测试用例 9: 闰年2月中 (能被400整除) - 日期递增正常
	@Test
	public void testMiddleOfFebruaryLeap_DivBy400() {
		Date inputDate = new Date(2, 15, 2000); // 2000年2月15日 (闰年)
		Date expectedDate = createDate(2, 16, 2000); // 2000年2月16日
		assertEquals(expectedDate, Nextday.nextDay(inputDate));
		// MC/DC 覆盖:
		// Month.getMonthSize: y.isLeap() -> true (对于 2000年)
		// Day.increment: currentPos <= m.getMonthSize() -> true (16 <= 29)
		// Year.isLeap: (条件已在之前的测试中覆盖)
	}


	// 测试用例 10: 年末
	@Test
	public void testEndOfYear() {
		Date inputDate = new Date(12, 31, 2023); // 2023年12月31日
		Date expectedDate = createDate(1, 1, 2024); // 2024年1月1日
		assertEquals(expectedDate, Nextday.nextDay(inputDate));
		// MC/DC 覆盖:
		// Date.increment: !d.increment() -> true
		// Day.increment: currentPos <= m.getMonthSize() -> false (32 > 31)
		// Date.increment: !m.increment() -> true
		// Month.increment: currentPos > 12 -> true (13 > 12)
		// Year.increment: currentPos == 0 -> false (2024 != 0)
	}

	// 测试用例 11: 年份从 -1 递增到 1 (Year.increment 中的特殊情况)
	@Test
	public void testYearIncrementFromMinusOne() {
		Date inputDate = new Date(12, 31, -1); // 公元前1年12月31日
		Date expectedDate = createDate(1, 1, 1);   // 公元1年1月1日 (跳过 0 年)
		assertEquals(expectedDate, Nextday.nextDay(inputDate));
		// MC/DC 覆盖:
		// Date.increment: !d.increment() -> true
		// Day.increment: currentPos <= m.getMonthSize() -> false (32 > 31)
		// Date.increment: !m.increment() -> true
		// Month.increment: currentPos > 12 -> true (13 > 12)
		// Year.increment: currentPos == 0 -> true (-1 + 1 == 0)
		// Year.isValid: this.currentPos != 0 -> true (对于 -1)
	}

	// 测试用例 12: 负数年份，闰年2月末 (-1 -> N=1, N%4=1)
	@Test
	public void testEndOfFebruaryNegativeLeap_Rule1() {
		Date inputDate = new Date(2, 29, -1); // 公元前1年2月29日 (根据逻辑是闰年)
		Date expectedDate = createDate(3, 1, -1); // 公元前1年3月1日
		assertEquals(expectedDate, Nextday.nextDay(inputDate));
		// MC/DC 覆盖:
		// Month.getMonthSize: y.isLeap() -> true (对于 -1)
		// Day.increment: currentPos <= m.getMonthSize() -> false (30 > 29)
		// Year.isLeap: (覆盖负数闰年规则1的条件)
		//   currentPos >= 0 -> false
		//   currentPos < 0 -> true
		//   (currentPos * -1) % 4 == 1 -> true (1 % 4 == 1)
		//   (currentPos * -1) % 100 != 1 -> true (1 % 100 != 1)
		//   -> 判定为 true
	}

	// 测试用例 13: 负数年份，闰年2月中 (-1) - 日期递增正常
	@Test
	public void testMiddleOfFebruaryNegativeLeap_Rule1() {
		Date inputDate = new Date(2, 15, -1); // 公元前1年2月15日 (闰年)
		Date expectedDate = createDate(2, 16, -1); // 公元前1年2月16日
		assertEquals(expectedDate, Nextday.nextDay(inputDate));
		// MC/DC 覆盖:
		// Month.getMonthSize: y.isLeap() -> true (对于 -1)
		// Day.increment: currentPos <= m.getMonthSize() -> true (16 <= 29)
		// Year.isLeap: (条件已在之前的测试中覆盖)
	}


	// 测试用例 14: 负数年份，非闰年2月末 (-2 -> N=2, N%4!=1)
	@Test
	public void testEndOfFebruaryNegativeNonLeap_Rule1Fail() {
		Date inputDate = new Date(2, 28, -2); // 公元前2年2月28日 (非闰年)
		Date expectedDate = createDate(3, 1, -2); // 公元前2年3月1日
		assertEquals(expectedDate, Nextday.nextDay(inputDate));
		// MC/DC 覆盖:
		// Month.getMonthSize: y.isLeap() -> false (对于 -2)
		// Day.increment: currentPos <= m.getMonthSize() -> false (29 > 28)
		// Year.isLeap: (覆盖负数非闰年的条件)
		//   currentPos >= 0 -> false
		//   currentPos < 0 -> true
		//   (currentPos * -1) % 4 == 1 -> false (2 % 4 != 1)
		//   -> 判定为 false
	}

	// 测试用例 15: 负数年份，非闰年2月末 (-101 -> N=101, N%100=1)
	@Test
	public void testEndOfFebruaryNegativeNonLeap_Rule2Fail() {
		Date inputDate = new Date(2, 28, -101); // 公元前101年2月28日 (非闰年)
		Date expectedDate = createDate(3, 1, -101); // 公元前101年3月1日
		assertEquals(expectedDate, Nextday.nextDay(inputDate));
		// MC/DC 覆盖:
		// Month.getMonthSize: y.isLeap() -> false (对于 -101)
		// Day.increment: currentPos <= m.getMonthSize() -> false (29 > 28)
		// Year.isLeap: (覆盖负数非闰年 /100 规则的条件)
		//   currentPos >= 0 -> false
		//   currentPos < 0 -> true
		//   (currentPos * -1) % 4 == 1 -> true (101 % 4 == 1)
		//   (currentPos * -1) % 100 != 1 -> false (101 % 100 == 1)
		//   (currentPos * -1) % 400 == 1 -> false (101 % 400 != 1)
		//   -> 判定为 false
	}

	// 测试用例 16: 负数年份，闰年2月末 (-401 -> N=401, N%400=1)
	@Test
	public void testEndOfFebruaryNegativeLeap_Rule3() {
		Date inputDate = new Date(2, 29, -401); // 公元前401年2月29日 (闰年)
		Date expectedDate = createDate(3, 1, -401); // 公元前401年3月1日
		assertEquals(expectedDate, Nextday.nextDay(inputDate));
		// MC/DC 覆盖:
		// Month.getMonthSize: y.isLeap() -> true (对于 -401)
		// Day.increment: currentPos <= m.getMonthSize() -> false (30 > 29)
		// Year.isLeap: (覆盖负数闰年 /400 规则的条件)
		//   currentPos >= 0 -> false
		//   currentPos < 0 -> true
		//   (currentPos * -1) % 4 == 1 -> true (401 % 4 == 1)
		//   (currentPos * -1) % 100 != 1 -> false (401 % 100 == 1)
		//   (currentPos * -1) % 400 == 1 -> true (401 % 400 == 1)
		//   -> 判定为 true
	}

	// 测试用例 17: 负数年份，闰年2月中 (-401) - 日期递增正常
	@Test
	public void testMiddleOfFebruaryNegativeLeap_Rule3() {
		Date inputDate = new Date(2, 15, -401); // 公元前401年2月15日 (闰年)
		Date expectedDate = createDate(2, 16, -401); // 公元前401年2月16日
		assertEquals(expectedDate, Nextday.nextDay(inputDate));
		// MC/DC 覆盖:
		// Month.getMonthSize: y.isLeap() -> true (对于 -401)
		// Day.increment: currentPos <= m.getMonthSize() -> true (16 <= 29)
		// Year.isLeap: (条件已在之前的测试中覆盖)
	}


	// --- 构造函数/Setter 验证的测试用例 (目标是 isValid 方法) ---

	@Test
	public void testInvalidDay_TooLow() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Date(1, 0, 2023); // 日期 0
		});
		assertEquals("Not a valid day", thrown.getMessage());
		// MC/DC 覆盖:
		// Day.isValid: this.currentPos >= 1 -> false
	}

	@Test
	public void testInvalidDay_TooHigh() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Date(1, 32, 2023); // 1月份的日期 32
		});
		assertEquals("Not a valid day", thrown.getMessage());
		// MC/DC 覆盖:
		// Day.isValid: this.currentPos <= m.getMonthSize() -> false (32 > 31)
	}

	@Test
	public void testInvalidDay_FebNonLeap() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Date(2, 29, 2023); // 2023年2月的日期 29
		});
		assertEquals("Not a valid day", thrown.getMessage());
		// MC/DC 覆盖:
		// Day.isValid: this.currentPos <= m.getMonthSize() -> false (29 > 28)
	}


	@Test
	public void testInvalidMonth_TooLow() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Date(0, 1, 2023); // 月份 0
		});
		assertEquals("Not a valid month", thrown.getMessage());
		// MC/DC 覆盖:
		// Month.isValid: this.currentPos >= 1 -> false
	}

	@Test
	public void testInvalidMonth_TooHigh() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Date(13, 1, 2023); // 月份 13
		});
		assertEquals("Not a valid month", thrown.getMessage());
		// MC/DC 覆盖:
		// Month.isValid: this.currentPos <= 12 -> false (13 > 12)
	}

	@Test
	public void testInvalidYear_Zero() {
		// 注意：错误消息是 "Not a valid month"，这似乎是 Year.setYear 中的复制粘贴错误
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Date(1, 1, 0); // 年份 0
		});
		assertEquals("Not a valid month", thrown.getMessage()); // 如果 Year.java 中的消息已修复，请调整此处
		// MC/DC 覆盖:
		// Year.isValid: this.currentPos != 0 -> false
	}

	// --- equals 方法的测试用例 (基本覆盖) ---
	// 这些测试间接帮助覆盖尚未测试到的 isValid 条件

	@Test
	public void testDateEquals_True() {
		Date d1 = new Date(5, 10, 2025);
		Date d2 = new Date(5, 10, 2025);
		assertTrue(d1.equals(d2));
		assertTrue(d2.equals(d1));
		assertEquals(d1.hashCode(), d2.hashCode()); // 良好实践
	}

	@Test
	public void testDateEquals_False_DifferentDay() {
		Date d1 = new Date(5, 10, 2025);
		Date d2 = new Date(5, 11, 2025);
		assertFalse(d1.equals(d2));
	}

	@Test
	public void testDateEquals_False_DifferentMonth() {
		Date d1 = new Date(5, 10, 2025);
		Date d2 = new Date(6, 10, 2025);
		assertFalse(d1.equals(d2));
	}

	@Test
	public void testDateEquals_False_DifferentYear() {
		Date d1 = new Date(5, 10, 2025);
		Date d2 = new Date(5, 10, 2026);
		assertFalse(d1.equals(d2));
	}

	@Test
	public void testDateEquals_False_DifferentType() {
		Date d1 = new Date(5, 10, 2025);
		String s = "Date";
		assertFalse(d1.equals(s));
	}

	@Test
	public void testDateEquals_False_Null() {
		Date d1 = new Date(5, 10, 2025);
		assertFalse(d1.equals(null));
	}

	@Test
	public void testToString() {
		Date d1 = new Date(9, 5, 2023);
		assertEquals("9/5/2023", d1.toString());
	}

	// 如果需要完整的类覆盖，为 Day, Month, Year 添加类似的 equals 基本测试，
	// 但 Date.equals() 测试已经驱动了它们的 equals 方法。示例：
	@Test
	public void testYearEquals() {
		Year y1 = new Year(2023);
		Year y2 = new Year(2023);
		Year y3 = new Year(2024);
		assertTrue(y1.equals(y2));
		assertFalse(y1.equals(y3));
		assertFalse(y1.equals("Year"));
		assertFalse(y1.equals(null));
	}

	@Test
	public void testPrintDate() {
		Date d = new Date(11, 22, 2023);
		// 验证输出 (可选但推荐)：
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;
		try {
			System.setOut(new PrintStream(outContent));
			d.printDate(); // 调用方法
			assertEquals("11/22/2023" + System.lineSeparator(), outContent.toString());
		} finally {
			System.setOut(originalOut); // 恢复标准输出
		}
		// 如果仅需要覆盖率，只需调用 d.printDate(); 即可。
	}

	@Test
	public void testDayEquals_True() {
		Year y = new Year(2023);
		Month m = new Month(10, y);
		Day d1 = new Day(15, m);
		Day d2 = new Day(15, m);
		assertTrue(d1.equals(d2));
		assertEquals(d1.hashCode(), d2.hashCode());
	}

	@Test
	public void testDayEquals_False_DifferentDay() {
		Year y = new Year(2023);
		Month m = new Month(10, y);
		Day d1 = new Day(15, m);
		Day d2 = new Day(16, m);
		assertFalse(d1.equals(d2));
	}

	@Test
	public void testDayEquals_False_DifferentMonth() {
		Year y = new Year(2023);
		Month m1 = new Month(10, y);
		Month m2 = new Month(11, y);
		Day d1 = new Day(15, m1);
		Day d2 = new Day(15, m2);
		assertFalse(d1.equals(d2)); // 因为 m1.equals(m2) 为 false
	}

	@Test
	public void testDayEquals_False_DifferentType() {
		Year y = new Year(2023);
		Month m = new Month(10, y);
		Day d1 = new Day(15, m);
		assertFalse(d1.equals("Day"));
	}

	@Test
	public void testDayEquals_False_Null() {
		Year y = new Year(2023);
		Month m = new Month(10, y);
		Day d1 = new Day(15, m);
		assertFalse(d1.equals(null));
	}

	// --- Month.equals 的测试用例 ---
	@Test
	public void testMonthEquals_True() {
		Year y = new Year(2023);
		Month m1 = new Month(10, y);
		Month m2 = new Month(10, y);
		assertTrue(m1.equals(m2));
		assertEquals(m1.hashCode(), m2.hashCode());
	}

	@Test
	public void testMonthEquals_False_DifferentMonth() {
		Year y = new Year(2023);
		Month m1 = new Month(10, y);
		Month m2 = new Month(11, y);
		assertFalse(m1.equals(m2));
	}

	@Test
	public void testMonthEquals_False_DifferentYear() {
		Year y1 = new Year(2023);
		Year y2 = new Year(2024);
		Month m1 = new Month(10, y1);
		Month m2 = new Month(10, y2);
		assertFalse(m1.equals(m2)); // 因为 y1.equals(y2) 为 false
	}

	@Test
	public void testMonthEquals_False_DifferentType() {
		Year y = new Year(2023);
		Month m1 = new Month(10, y);
		assertFalse(m1.equals("Month"));
	}

	@Test
	public void testMonthEquals_False_Null() {
		Year y = new Year(2023);
		Month m1 = new Month(10, y);
		assertFalse(m1.equals(null));
	}

	// --- isValid 的测试用例 (通过构造函数/setters 间接测试) ---
	// 现有的无效日期测试 (testInvalidDay_TooLow 等) 已经
	// 覆盖了 isValid() 返回 false 并导致异常的路径。
	// 其他测试中成功的 Date 创建覆盖了 isValid() 返回 true 的路径。

	// 如果需要，我们可以添加 *直接* 调用 isValid 的测试，尽管可能有些冗余。
	@Test
	public void testDayIsValidDirect() {
		Year y = new Year(2023);
		Month m = new Month(4, y); // 四月, 30 天
		Day validDay = new Day(30, m);
		assertTrue(validDay.isValid());

		// 手动创建无效状态 (如果可能，绕过初始构造函数检查，
		// 尽管当前设计使得不使用反射很难做到)
		// 对于覆盖率工具，有时测试边缘情况会有帮助：
		Day dayToCheck = new Day(1,m); // 初始状态有效
		dayToCheck.setCurrentPos(31); // 手动设置为对四月无效的状态
		assertFalse(dayToCheck.isValid()); // 直接检查 isValid
		dayToCheck.setCurrentPos(0); // 手动设置为无效状态
		assertFalse(dayToCheck.isValid()); // 直接检查 isValid
	}

	@Test
	public void testMonthIsValidDirect() {
		Year y = new Year(2023);
		Month validMonth = new Month(12, y);
		assertTrue(validMonth.isValid());

		Month monthToCheck = new Month(1, y); // 初始状态有效
		monthToCheck.setCurrentPos(13); // 手动设置为无效状态
		assertFalse(monthToCheck.isValid());
		monthToCheck.setCurrentPos(0); // 手动设置为无效状态
		assertFalse(monthToCheck.isValid());
	}

	// 注意：testInvalidYear_Zero 已经覆盖了 Year.isValid() 返回 false 的情况。
	@Test
	public void testYearIsValidDirect() {
		Year validYear = new Year(2023);
		assertTrue(validYear.isValid());
		Year validNegativeYear = new Year(-1);
		assertTrue(validNegativeYear.isValid());
		// Year(0) 抛出异常，覆盖了 false 路径。
	}

	//--- Year.isLeap() 的覆盖 ---
	// 现有的 testEndOfFebruary... 测试已经广泛覆盖了 nextDay 的逻辑。
	// 让我们添加对 isLeap 的直接调用，以确保所有分支都被覆盖，如果
	// 覆盖率工具对直接调用与间接使用比较挑剔。

	@Test
	public void testIsLeapDirectly() {
		// 正数年份情况
		assertTrue(new Year(2000).isLeap()); // 能被 400 整除
		assertFalse(new Year(2100).isLeap()); // 能被 100 整除，但不能被 400 整除
		assertTrue(new Year(2024).isLeap()); // 能被 4 整除，但不能被 100 整除
		assertFalse(new Year(2023).isLeap()); // 不能被 4 整除

		// 负数年份情况 (N = year * -1)
		assertTrue(new Year(-1).isLeap());   // N=1. 1%4=1, 1%100!=1 -> true
		assertFalse(new Year(-2).isLeap());  // N=2. 2%4!=1 -> false
		assertFalse(new Year(-101).isLeap());// N=101. 101%4=1, 101%100=1, 101%400!=1 -> false
		assertTrue(new Year(-401).isLeap()); // N=401. 401%4=1, 401%100=1, 401%400=1 -> true
	}

	// --- 测试 Year 构造函数中不正确的异常消息 ---
	// 这个测试通过是因为异常的 *类型* 是正确的，
	// 即使消息具有误导性。它确保了验证逻辑的运行。
	@Test
	public void testInvalidYear_Zero_ChecksExceptionType() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Year(0); // 年份 0
		});
		// 如果需要文档说明，可以选择断言 *实际的* (不正确的) 消息
		// assertEquals("Not a valid month", thrown.getMessage());
		// 或者像 assertThrows 那样，仅断言 *某个* 异常被抛出。
	}

	@Test
	public void testNextday(){

		Nextday d = new Nextday();
		assertNotNull(d);
	}

	@Test
	public void testMonthIsValidWithNullYearField() throws Exception {
		// 首先创建一个有效的 Month
		Year validYear = new Year(2025);
		Month month = new Month(5, validYear);
		// 使用反射将内部的 y 字段设置为 null。
		java.lang.reflect.Field yField = Month.class.getDeclaredField("y");
		yField.setAccessible(true);
		yField.set(month, null);
		// 现在，isValid() 应该会执行 y != null 为 false 的分支。
		assertFalse(month.isValid());
	}

	@Test
	public void testMonthIsValidWithInvalidYearField() throws Exception {
		// 首先创建一个有效的 Month
		Year validYear = new Year(2025);
		Month month = new Month(5, validYear);
		// 使用反射强制使内部的年份无效。
		// 在 Year 中，当 currentPos == 0 时，isValid() 返回 false。
		java.lang.reflect.Field currentPosField = CalendarUnit.class.getDeclaredField("currentPos");
		currentPosField.setAccessible(true);
		currentPosField.set(validYear, 0); // 使年份无效。
		// 现在，y != null 为 true，但 y.isValid() 返回 false。
		assertFalse(month.isValid());
	}

	// --- 使用反射测试 Day.isValid() 边缘情况的用例 ---

	@Test
	public void testDayIsValidWithNullMonth() throws Exception {
		// 1. 首先创建一个有效的 Date/Day
		Date date = new Date(1, 1, 2023); // 内部创建了一个 Day 对象
		// 需要访问 Day 对象。假设 Date 有 getter 或使用反射
		// 假设 Date 类结构为: private Day d;
		Field dayField = Date.class.getDeclaredField("d");
		dayField.setAccessible(true);
		Day day = (Day) dayField.get(date);

		// 2. 使用反射将 Day 内部的 'm' 字段设置为 null
		Field monthField = Day.class.getDeclaredField("m");
		monthField.setAccessible(true);
		monthField.set(day, null);

		// 3. 调用 isValid() 并断言 false (因为 m == null)
		assertFalse(day.isValid(), "当 Day 的月份为 null 时，isValid() 应返回 false");
	}

	@Test
	public void testDayIsValidWithInvalidMonth() throws Exception {
		// 1. 首先创建一个有效的 Date/Day
		Date date = new Date(1, 1, 2023);
		// 访问 Day 对象
		Field dayField = Date.class.getDeclaredField("d");
		dayField.setAccessible(true);
		Day day = (Day) dayField.get(date);

		// 访问与 Day 关联的 Month 对象
		Field monthField = Day.class.getDeclaredField("m");
		monthField.setAccessible(true);
		Month month = (Month) monthField.get(day);

		// 2. 使 Month 对象无效 (例如，通过使其 Year 无效)
		// 访问 Month 内的 Year 对象
		Field yearField = Month.class.getDeclaredField("y");
		yearField.setAccessible(true);
		Year year = (Year) yearField.get(month);

		// 通过将其 currentPos 设置为 0 来使 Year 无效
		Field currentPosField = CalendarUnit.class.getDeclaredField("currentPos");
		currentPosField.setAccessible(true);
		currentPosField.set(year, 0); // 年份 0 是无效的

		// 预检查：确保月份现在是无效的
		assertFalse(month.isValid(), "月份在其年份无效后应该变为无效");

		// 3. 调用 Day.isValid() 并断言 false (因为 m.isValid() 为 false)
		assertFalse(day.isValid(), "当 Day 的月份无效时，isValid() 应返回 false");

		// 恢复年份以避免 'date' 对象重用时的副作用 (可选)
		currentPosField.set(year, 2023);
	}

	// --- 重新验证 Year.isLeap() 覆盖率 ---
	// 现有的 'testIsLeapDirectly' 测试已经覆盖了负数输入。
	// 如果覆盖率工具仍然报告 'else if (currentPos < 0)' 未覆盖，
	// 这可能是工具的限制或误解。该逻辑路径
	// 肯定被以下测试执行过：
	// testIsLeapDirectly() 使用 new Year(-1), new Year(-2) 等
	// testEndOfFebruaryNegativeLeap_Rule1() 使用年份 -1
	// testEndOfFebruaryNegativeNonLeap_Rule1Fail() 使用年份 -2
	// 根据代码流程，此行似乎不需要额外的逻辑测试。

	@Test
	public void testYearIsLeapPositive() {
		Year leap1 = new Year(2024);    // 2024年：闰年
		Year nonLeap = new Year(2023);    // 非闰年
		Year nonLeapCentury = new Year(2100); // 能被100整除但不能被400整除：非闰年
		Year leapCentury = new Year(2000);    // 能被400整除：闰年
		assertTrue(leap1.isLeap());
		assertFalse(nonLeap.isLeap());
		assertFalse(nonLeapCentury.isLeap());
		assertTrue(leapCentury.isLeap());
	}

	@Test
	public void testYearIsLeapNegative() {
		Year negLeap = new Year(-5); // (-5 * -1 = 5; 5 % 4 == 1 且 5 % 100 != 1) → true
		Year negNonLeap = new Year(-4); // (-4 * -1 = 4; 4 % 4 == 0) → false
		assertTrue(negLeap.isLeap());
		assertFalse(negNonLeap.isLeap());
	}
}
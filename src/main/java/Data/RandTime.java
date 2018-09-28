package Data;
/**
 *@Author:LIZHIYIN
 *@Desciption:随机生成时间区间；
 *@para:
 *@Date:Created in 20180311
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RandTime {
	public String RandTime() {
		int i = new Random().nextInt(10) + 1;
		Date randomDateMIN = randomDate("2010-09-20", "2017-02-04");
		// 加入比较大小，产生一个随机区间的；
		Date randomDateMAX = randomDate("2010-09-20", "2017-02-04");

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String resulttimeMIN = format.format(randomDateMIN);// 构造开始日期 HH:mm:ss
		String resulttimeMAX = format.format(randomDateMAX);
		// System.out.println(resulttime+"----"+i);
		int t = randomDateMIN.compareTo(randomDateMAX);
		// 判断两个随机时间的大小，是否构成一个区间；
		if (t < 0) {
			return resulttimeMIN + "," + resulttimeMAX;
		} else {
			return resulttimeMAX + "," + resulttimeMIN;

		}
	}

	private static Date randomDate(String beginDate, String endDate) {

		try {

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			Date start = format.parse(beginDate);// 构造开始日期

			Date end = format.parse(endDate);// 构造结束日期

			// getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。

			if (start.getTime() >= end.getTime()) {

				return null;

			}

			long date = random(start.getTime(), end.getTime());

			return new Date(date);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	private static long random(long begin, long end) {

		long rtn = begin + (long) (Math.random() * (end - begin));

		// 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值

		if (rtn == begin || rtn == end) {

			return random(begin, end);

		}

		return rtn;

	}
}

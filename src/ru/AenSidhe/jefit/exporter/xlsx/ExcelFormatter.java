package ru.AenSidhe.jefit.exporter.xlsx;

import java.io.*;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;
import org.joda.time.*;
import android.content.*;
import android.os.*;
import android.widget.*;

public class ExcelFormatter
{
	public ExcelFormatter(Context context)
	{
		_context = context;
	}

	public String CreateExcel(Iterator<Set> sets) throws IOException
	{
		Workbook wb = new HSSFWorkbook();
		String fileName = String.format("jefit_%s.xls", _now.toString("yyyyMMdd_HHmmss"));
		File file = Utils.GetFileOnSdCard(fileName, true);
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.close();
		return file.getAbsolutePath();
	}

	private final DateTime _now = new DateTime();
	
	private final Context _context;
}

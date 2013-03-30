package ru.AenSidhe.jefit.exporter.xlsx;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.FileNotFoundException;
import java.util.*;
import java.io.*;

public class Exporter
{

	public Exporter(LocalDate start, LocalDate end, Boolean usePro)
	{
		if (start != null && end != null && start.isAfter(end))
		{
			_end = start;
			_start = end;
		}
		else
		{
			_start = start;
			_end = end;
		}
		_usePro = usePro;
	}

	public List<Set> getData()
	{
		return _data;
	}

	public Exporter readData() throws FileNotFoundException, IOException
	{
		SQLiteDatabase db = null;
		_data = new ArrayList<Set>();
		try
		{
			db = SQLiteDatabase.openDatabase(getDatabasePath(), null, SQLiteDatabase.OPEN_READONLY);
			if (db == null)
				throw new FileNotFoundException("database wasn't found");

			String condition;
			String[] args;

			if (_start != null && _end != null)
			{
				condition = _bothDateCondition;
				args = new String[]{ _formatter.print(_start), _formatter.print(_end) };
			}
			else if (_start == null && _end == null)
			{
				condition = _noDateCondition;
				args = null;
			}
			else
			{
				LocalDate date = _start == null ? _end : _start;
				String operator = _start == null ? "<=" : ">=";
				condition = String.format(_oneDateCondition, operator);
				args = new String[] {_formatter.print(date)};
			}

			Cursor cursor = db.rawQuery(String.format(_query, condition), args);

			while (cursor.moveToNext())
			{
				_data.add(new Set(cursor.getString(0), cursor.getString(1), LocalDate.parse(cursor.getString(2), _formatter)));
				//if (_data.size() == 2)
					//break;
			}
			cursor.close();
		}
		finally
		{
			if (db != null)
				db.close();
		}

		return this;
	}

	private String getDatabasePath() throws IOException
	{
		return Utils.GetFileOnSdCard("jefit", _usePro ? "jefit_pro.bak" : "jefit.bak", false).getAbsolutePath();
	}
	
	private final LocalDate _start;
	private final LocalDate _end;
	private List<Set> _data;
	private final Boolean _usePro;

	private final static String _dateTimePattern = "yyyy-MM-dd";
	private final DateTimeFormatter _formatter = DateTimeFormat.forPattern(_dateTimePattern);

	private final static String _query =
		"select el.ename, el.logs, el.mydate " +
		"from exerciseLogs el " +
			"left outer join cardioLogs cl on el._id = cl.eid " +
		"where cl._id is null and %s" +
		"order by el.mydate";

	private final static String _bothDateCondition = "el.mydate between ? and ? ";

	private final static String _oneDateCondition = "el.mydate %s ? ";

	private final static String _noDateCondition = "1 = 1 ";
}

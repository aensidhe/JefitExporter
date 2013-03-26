package ru.AenSidhe.jefit.exporter.xlsx;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CancellationSignal;
import org.joda.time.LocalDate;

import java.io.FileNotFoundException;

public class Exporter
{
	private final LocalDate _start;
	private final LocalDate _end;
	private int _data;

	public Exporter(LocalDate start, LocalDate end)
	{
		_start = start;
		_end = end;
	}

	public int getData()
	{
		return _data;
	}

	public Exporter readData() throws FileNotFoundException
	{
		SQLiteDatabase db = null;
		try
		{
			db = SQLiteDatabase.openDatabase("/sdcard/jefit/jefit.bak", null, SQLiteDatabase.OPEN_READONLY);
			if (db == null)
				throw new FileNotFoundException("database wasn't found");

			Cursor cursor = db.rawQuery("select count(*) from sys.tables", null);
			_data = cursor.getInt(0);
		}
		finally
		{
			if (db != null)
				db.close();
		}

		return this;
	}
}

package ru.AenSidhe.jefit.exporter.xlsx;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import org.joda.time.LocalDate;

public class DateTimeFragment extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);
		_view = inflater.inflate(R.layout.date_time_fragment, container, false);

		_view.findViewById(R.id.date_time_fragment_show_dialog_button).setOnClickListener(new DialogActivator());
		_view.findViewById(R.id.date_time_fragment_clear_button).setOnClickListener(new TextClearer());

		if (_editorHint != null)
			getEditor().setHint(_editorHint);

		return _view;
	}

	@Override public void onInflate(Activity activity, AttributeSet set, Bundle savedInstanceState)
	{
		Context context = activity.getBaseContext();
		TypedArray a = context.obtainStyledAttributes(set, R.styleable.DateTimeFragment);
		_dialogTitle = a.getString(R.styleable.DateTimeFragment_DialogTitle);
		_editorHint = a.getString(R.styleable.DateTimeFragment_HintText);
		a.recycle();
	}

	public LocalDate getDate()
	{
		final String s = getEditor().getText().toString();
		if (s == null || s.length() == 0)
			return null;

		try
		{
			return LocalDate.parse(getEditor().getText().toString());
		}
		catch (Exception e)
		{
			return null;
		}
	}

	private View _view;

	private CharSequence _dialogTitle;

	private CharSequence _editorHint;

	private EditText getEditor()
	{
		return  (EditText) _view.findViewById(R.id.date_time_fragment_editor);
	}

	private class DialogActivator implements View.OnClickListener, DatePickerDialog.OnDateSetListener
	{
		@Override
		public void onClick(View v)
		{
			try
			{
				_date = LocalDate.parse(getEditor().getText().toString());
			}
			catch (Exception e)
			{
				_date = LocalDate.now();
			}

			_dialog = new DatePickerDialog(getActivity(), this, _date.getYear(), _date.getMonthOfYear() - 1, _date.getDayOfMonth());
			_dialog.setTitle(_dialogTitle);
			_dialog.show();
		}

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
		{
			_date = new LocalDate(year, monthOfYear + 1, dayOfMonth);
			getEditor().setText(_date.toString());
			_dialog.dismiss();
		}

		private LocalDate _date;
		private DatePickerDialog _dialog;
	}

	private class TextClearer implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			getEditor().setText("");
		}
	}
}
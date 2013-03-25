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

import java.util.Calendar;

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
			setHint(_editorHint);

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

	public DateTimeFragment setHint(CharSequence text)
	{
		getEditor().setHint(text);
		return this;
	}

	public CharSequence getText()
	{
		return getEditor().getText().toString();
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
            Calendar c = Calendar.getInstance();

			DatePickerDialog d = new DatePickerDialog(getActivity(), this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
			d.setTitle(_dialogTitle);
			d.show();
		}

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
		{
			getEditor().setText(String.format("%d-%d-%d", year, monthOfYear, dayOfMonth));
		}
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
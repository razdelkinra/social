import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

export const DatePickerField = ({
  input: { onChange, value },
  placeholder,
  ...rest
}) => (
  <DatePicker
    className="date-picker"
    placeholderText={placeholder}
    selected={value}
    onChange={(date) => onChange(date)}
    {...rest}
  />
);

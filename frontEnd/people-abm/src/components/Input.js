function Input(props) {
  const { options, type, value, onChange } = props;

  if (undefined === options) {
    return (
      <input
        type={type ?? "text"}
        className="form-control"
        value={value ?? ""}
        onChange={onChange}
      ></input>
    );
  } else
    return (
      <select className="form-control" value={value ?? ""} onChange={onChange}>
        <option key="null" value={""}></option>
        {options.map((option) => (
          <option key={option.value} value={option.value}>
            {option.label}
          </option>
        ))}
      </select>
    );
}

export default Input;

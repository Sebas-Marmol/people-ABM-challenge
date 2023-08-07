import Modal from "./Modal";
import Input from "./Input";
import React, { useState } from "react";

function PersonForm(props) {
  const {
    fields,
    isEdit,
    close,
    accept,
    originalPerson,
    isVisible,
    errors,
    disabled,
  } = props;
  const title = isEdit
    ? `Editar persona "${originalPerson.perId}"`
    : "Nueva persona";

  const [person, setPerson] = useState(originalPerson);

  React.useEffect(() => {
    setPerson(props.originalPerson);
  }, [props.originalPerson]);

  const handleChange = (event, key) => {
    setPerson({ ...person, [key]: event.currentTarget.value });
  };

  const inputFields = () =>
    fields.map((field) => (
      <div>
        <label>{field.label}</label>
        <Input
          options={field.options}
          value={person[field.key]}
          type={field.type}
          onChange={(e) => handleChange(e, field.key)}
        />
      </div>
    ));

  return (
    <Modal
      title={title}
      isVisible={isVisible}
      close={() => close(person)}
      accept={() => accept(person)}
      disabled={disabled}
    >
      {inputFields()}
      {errors.map((error) => (
        <p className="text-danger"> {error} </p>
      ))}
    </Modal>
  );
}

export default PersonForm;

import React, { useEffect, useState, useCallback, useRef } from "react";
import Table from "react-data-table-component";
import { Button, Spinner } from "react-bootstrap";
import PersonForm from "../components/PersonForm";
import { formatDate } from "../utils/utils";
import Modal from "../components/Modal";
import Filter from "../components/Filter";

function People() {
  const baseUrl = process.env.REACT_APP_BASE_URL;

  const [people, setPeople] = useState([]);
  const [showPersonForm, setShowPersonForm] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [personToDeleteId, setPersonToDeleteId] = useState({});
  const [loading, setLoading] = useState(true);
  const [sending, setSending] = useState(false);
  const [errors, setErrors] = useState([]);
  const [deleteError, setDeleteError] = useState("");
  const [filtersMessage, setFiltersMessage] = useState("");

  const newPerson = useRef({});
  const editedPerson = useRef({});
  const isEdit = useRef(false);
  const filters = useRef("");

  const inputFields = [
    { key: "perNombre", label: "Nombre" },
    { key: "perApellido", label: "Apellido" },
    {
      key: "perNumeroDocumento",
      label: "Número de Documento",
    },
    {
      key: "perTipoDocumento",
      label: "Tipo de Documento",
      options: [
        { label: "Dni", value: "Dni" },
        { label: "Cédula", value: "Cédula" },
        { label: "Pasaporte", value: "Pasaporte" },
      ],
    },
    { key: "perFechaNacimiento", label: "Fecha de Nacimiento", type: "date" },
  ];

  const filterFields = [
    {
      label: "Tipo de Documento",
      key: "documento",
      options: [
        { label: "Dni", value: "Dni" },
        { label: "Cédula", value: "Cédula" },
        { label: "Pasaporte", value: "Pasaporte" },
      ],
    },
    { label: "Nombre/Apellido", key: "nombre" },
  ];

  const columns = [
    { name: "id", selector: (row) => row.perId },
    { name: "Nombre", selector: (row) => row.perNombre },
    { name: "Apellido", selector: (row) => row.perApellido },
    { name: "Número Documento", selector: (row) => row.perNumeroDocumento },
    { name: "Tipo Documento", selector: (row) => row.perTipoDocumento },
    {
      name: "Fecha Nacimiento",
      selector: (row) => formatDate(row.perFechaNacimiento),
    },
    {
      name: "Acciones",
      cell: (row) => (
        <div>
          <Button
            disabled={sending}
            class="btn btn-primary"
            onClick={() => {
              openPersonForm(row);
            }}
          >
            Editar
          </Button>
          <Button
            disabled={sending}
            class="ml-5 btn btn-danger"
            onClick={() => {
              openDeleteModal(row);
            }}
          >
            <i class="bi bi-trash"></i>
          </Button>
        </div>
      ),
    },
  ];

  const validatePerson = (person) => {
    let errorMessages = [];
    inputFields.forEach((field) => {
      const value = person[field.key];
      if ("" === value || null === value || undefined === value) {
        errorMessages.push(`El valor ${field.label} no puede estar vacío`);
      }
    });
    if (
      person.perNumeroDocumento !== undefined &&
      isNaN(person.perNumeroDocumento)
    ) {
      errorMessages.push(`El número de documento debe ser un valor numérico`);
    }
    setErrors(errorMessages);
    return 0 === errorMessages.length;
  };

  const insertPerson = async () => {
    if (false === validatePerson(newPerson.current)) {
      return;
    }
    const response = await fetch(baseUrl, {
      method: "POST",
      body: JSON.stringify(newPerson.current),
      headers: {
        "Content-type": "application/json",
      },
    });
    if (201 === response.status) {
      setErrors([]);
      setShowPersonForm(false);
      newPerson.current = {};
      fetchData();
    } else {
      const data = await response.json();
      setErrors([data.message]);
    }
  };

  const updatePerson = async () => {
    if (false === validatePerson(editedPerson.current)) {
      return;
    }
    const response = await fetch(`${baseUrl}/${editedPerson.current.perId}`, {
      method: "PATCH",
      body: JSON.stringify(editedPerson.current),
      headers: {
        "Content-type": "application/json",
      },
    });
    if (200 === response.status) {
      setErrors([]);
      setShowPersonForm(false);
      fetchData();
    } else {
      const data = await response.json();
      setErrors([data.message]);
    }
  };

  const deletePerson = async () => {
    setSending(true);
    const response = await fetch(`${baseUrl}/${personToDeleteId}`, {
      method: "DELETE",
    });
    if (200 === response.status) {
      setDeleteError("");
      setShowDeleteModal(false);
      fetchData();
    } else {
      const data = await response.json();
      setDeleteError(data.message);
    }
    setSending(false);
  };

  const submitPerson = async (person) => {
    setSending(true);
    savePersonFromForm(person);
    if (true === isEdit.current) {
      await updatePerson();
    } else {
      await insertPerson();
    }
    setSending(false);
  };

  const openPersonForm = (row) => {
    setErrors([]);
    if (undefined === row) {
      isEdit.current = false;
    } else {
      isEdit.current = true;
      editedPerson.current = row;
    }
    setShowPersonForm(true);
  };

  const openDeleteModal = (row) => {
    setDeleteError("");
    setPersonToDeleteId(row.perId);
    setShowDeleteModal(true);
  };

  const closeDeleteModal = () => {
    setShowDeleteModal(false);
  };

  const savePersonFromForm = (person) => {
    if (true === isEdit.current) {
      editedPerson.current = person;
    } else {
      newPerson.current = person;
    }
  };

  const closePersonForm = (person) => {
    savePersonFromForm(person);
    setShowPersonForm(false);
  };

  const updateFilters = (newFilters) => {
    setFiltersMessage(
      newFilters !== "" ? "Los resultados actuales están filtrados" : ""
    );
    filters.current = newFilters;
    fetchData();
  };

  const fetchData = useCallback(async () => {
    setLoading(true);
    const data = await fetch(`${baseUrl}?${filters.current}`);
    let people = await data.json();
    people.forEach((person) => {
      person.perFechaNacimiento = person.perFechaNacimiento.substring(0, 10);
    });
    setPeople(people);
    setLoading(false);
  }, [baseUrl]);

  useEffect(() => {
    fetchData();
  }, [fetchData]);

  return (
    <div>
      <Filter
        fields={filterFields}
        disabled={sending}
        onUpdateFilter={(filters) => updateFilters(filters)}
      />
      <PersonForm
        fields={inputFields}
        isEdit={isEdit.current}
        isVisible={showPersonForm}
        originalPerson={
          true === isEdit.current ? editedPerson.current : newPerson.current
        }
        close={(person) => closePersonForm(person)}
        accept={(person) => submitPerson(person)}
        errors={errors}
        disabled={sending}
      />
      <Modal
        title={`Eliminar persona "${personToDeleteId}"?`}
        disabled={sending}
        isVisible={showDeleteModal}
        close={(person) => closeDeleteModal(person)}
        accept={(person) => deletePerson(person)}
      >
        <p className="text-danger"> {deleteError} </p>
      </Modal>
      <div class="mt-5 px-5">
        <Button disabled={sending} onClick={() => openPersonForm()}>
          <i class="bi bi-plus"></i> Nueva persona
        </Button>
        <p className="text-danger"> {filtersMessage} </p>
        <Table
          columns={columns}
          data={people}
          pagination
          persistTableHead
          progressPending={loading}
          progressComponent={
            <Spinner animation="border" role="status"></Spinner>
          }
          noDataComponent={<h1>No se encontraron registros...</h1>}
        />
      </div>
    </div>
  );
}

export default People;

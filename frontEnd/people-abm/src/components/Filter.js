import React, { useState } from "react";
import Input from "./Input";
import { Button } from "react-bootstrap";

function Filter(props) {
  const { fields, disabled, onUpdateFilter } = props;
  const [filters, setFilters] = useState({});

  const clearFilters = () => {
    setFilters({});
  };

  const handleChange = (event, key) => {
    setFilters({ ...filters, [key]: event.currentTarget.value });
  };

  const returnFilters = () => {
    let filterStrings = [];
    Object.keys(filters).forEach((filterKey) => {
      const value = filters[filterKey];
      if (value !== null && value !== undefined && value !== "") {
        filterStrings.push(`${filterKey}=${value}`);
      }
    });
    onUpdateFilter(filterStrings.join("&"));
  };

  return (
    <div class="container">
      <div class="row">
        {fields.map((field) => (
          <div class="col-3">
            <label>{field.label}</label>
            <Input
              options={field.options}
              value={filters[field.key]}
              type={field.type}
              onChange={(e) => handleChange(e, field.key)}
            />
          </div>
        ))}
        <div class="col-2 mt-4">
          <Button
            disabled={disabled}
            variant="secondary"
            onClick={clearFilters}
          >
            Limpiar
          </Button>
        </div>
        <div class="col-2 mt-4">
          <Button
            class="col-2"
            disabled={disabled}
            variant="primary"
            onClick={returnFilters}
          >
            Buscar
          </Button>
        </div>
      </div>
    </div>
  );
}

export default Filter;

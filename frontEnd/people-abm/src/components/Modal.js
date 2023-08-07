import Button from "react-bootstrap/Button";
import BModal from "react-bootstrap/Modal";

function Modal(props) {
  const {
    title,
    isVisible,
    close,
    accept,
    closeLabel,
    acceptLabel,
    disabled,
    children,
  } = props;

  return (
    <BModal
      show={isVisible}
      backdrop="static"
      onHide={() => close()}
      centered={true}
    >
      <BModal.Header closeButton>
        <BModal.Title>{title}</BModal.Title>
      </BModal.Header>
      <BModal.Body>{children}</BModal.Body>
      <BModal.Footer>
        <Button disabled={disabled} variant="secondary" onClick={() => close()}>
          {closeLabel ?? "Cancelar"}
        </Button>
        <Button disabled={disabled} variant="primary" onClick={() => accept()}>
          {acceptLabel ?? "Aceptar"}
        </Button>
      </BModal.Footer>
    </BModal>
  );
}

export default Modal;

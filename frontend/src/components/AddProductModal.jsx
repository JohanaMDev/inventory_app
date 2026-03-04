import { useState, useEffect } from "react";
import axios from "axios";

// 1. Estado inicial limpio (Mantenemos fuera lo que es estático)
const initialFormState = {
  nombre: "",
  codigoBarras: "",
  descripcion: "",
  precioCosto: "",
  marca: "",
  categoriaId: "",
  stockActual: "",
  stockMinimo: "",
  precioVenta: "",
  activo: true,
};

const AddProductModal = ({
  isOpen,
  onClose,
  onRefresh,
  categories = [],
  productToEdit = null,
}) => {
  const [formData, setFormData] = useState({
    ...initialFormState,
    margenGanancia: "30",
    iva: "21",
  });

  const [showNewCatInput, setShowNewCatInput] = useState(false);
  const [newCatName, setNewCatName] = useState("");

  const resetForm = () => {
    setFormData((prev) => ({
      ...initialFormState,
      margenGanancia: prev.margenGanancia,
      iva: prev.iva,
    }));
  };

  const inputStyle =
    "w-full bg-slate-800/40 border border-white/10 rounded-lg p-2 text-sm text-white outline-none focus:border-blue-500 transition-all [appearance:textfield] [&::-webkit-outer-spin-button]:appearance-none [&::-webkit-inner-spin-button]:appearance-none";
  const labelStyle =
    "text-[10px] font-bold text-slate-500 uppercase tracking-wider ml-1 mb-1 block";

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => {
      const newData = { ...prev, [name]: value };
      if (["precioCosto", "margenGanancia", "iva"].includes(name)) {
        const c = parseFloat(newData.precioCosto) || 0;
        const m = parseFloat(newData.margenGanancia) || 0;
        const i = parseFloat(newData.iva) || 0;
        newData.precioVenta = (c * (1 + m / 100) * (1 + i / 100)).toFixed(2);
      }
      return newData;
    });
  };

  const handleCreateCategory = async () => {
    if (!newCatName.trim()) return;
    const token = localStorage.getItem("token");
    try {
      const resp = await axios.post(
        "http://localhost:8080/api/categorias",
        { nombre: newCatName, activo: true },
        { headers: { Authorization: `Bearer ${token}` } },
      );
      onRefresh();
      setFormData((prev) => ({ ...prev, categoriaId: resp.data.id }));
      setShowNewCatInput(false);
      setNewCatName("");
    } catch (error) {
      alert("Error al crear categoría");
    }
  };

  const handleClose = () => {
    resetForm();
    onClose();
  };

  useEffect(() => {
    if (productToEdit) {
      setFormData({
        ...productToEdit,
        // Aseguramos que los IDs y valores numéricos sean strings para los inputs
        categoriaId: productToEdit.categoriaProductos?.id || "",
        precioCosto: productToEdit.precioCosto || "",
        margenGanancia: productToEdit.margenGanancia || "30",
        iva: productToEdit.iva || "21",
        precioVenta: productToEdit.precio || "",
      });
    } else {
      resetForm(); // Si no hay producto, limpiar para nuevo registro
    }
  }, [productToEdit, isOpen]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem("token");
    const isEditing = !!productToEdit;
    const url = isEditing
      ? `http://localhost:8080/api/productos/${productToEdit.id}`
      : "http://localhost:8080/api/productos";
    try {
      const payload = {
        ...formData,
        categoriaProductos: formData.categoriaId
          ? { id: parseInt(formData.categoriaId) }
          : null,
        precioCosto: parseFloat(formData.precioCosto || 0),
        precio: formData.precioVenta ? parseFloat(formData.precioVenta) : null,
        stockActual: parseInt(formData.stockActual || 0),
        stockMinimo: parseInt(formData.stockMinimo || 0),
        activo: formData.activo,
      };
      if (isEditing) {
        await axios.put(url, payload, {
          headers: { Authorization: `Bearer ${token}` },
        });
      } else {
        await axios.post(url, payload, {
          headers: { Authorization: `Bearer ${token}` },
        });
      }

      {
        /*onRefresh(response.data.id);*/
      }
      onRefresh(isEditing ? productToEdit.id : null);
      resetForm();
      onClose();
    } catch (error) {
      console.error("Error detallado:", error.response?.data); // 💡 Esto te dirá el error real del backend
      alert(
        "Error al guardar producto: " +
          (error.response?.data?.message || "Consulte los logs"),
      );
    }
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-slate-950/80 backdrop-blur-sm p-4">
      <div className="bg-slate-900 border border-white/10 w-full max-w-4xl rounded-2xl p-6 shadow-2xl overflow-hidden">
        <div className="flex justify-between items-center mb-5 border-b border-white/5 pb-3">
          <h2 className="text-xl font-bold text-white">Nuevo Producto</h2>
          <button
            onClick={handleClose}
            className="text-slate-500 hover:text-white transition-colors text-lg"
          >
            ✕
          </button>
        </div>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div>
              <label className={labelStyle}>Nombre</label>
              <input
                type="text"
                name="nombre"
                placeholder="Nombre"
                required
                className={inputStyle}
                onChange={handleChange}
                value={formData.nombre}
              />
            </div>
            <div>
              <label className={labelStyle}>Código de Barras</label>
              <input
                type="text"
                name="codigoBarras"
                placeholder="ID Único"
                required
                className={inputStyle}
                onChange={handleChange}
                value={formData.codigoBarras}
              />
            </div>
            <div>
              <label className={labelStyle}>Marca</label>
              <input
                type="text"
                name="marca"
                placeholder="Marca"
                className={inputStyle}
                onChange={handleChange}
                value={formData.marca}
              />
            </div>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div className="md:col-span-2">
              <label className={labelStyle}>Descripción</label>
              <input
                type="text"
                name="descripcion"
                placeholder="Descripción"
                className={inputStyle}
                onChange={handleChange}
                value={formData.descripcion}
              />
            </div>
            <div>
              <label className={labelStyle}>Categoría</label>
              <div className="flex gap-1">
                <select
                  name="categoriaId"
                  value={formData.categoriaId}
                  onChange={handleChange}
                  className={`${inputStyle} cursor-pointer`}
                >
                  <option value="">Sin Categoría</option>
                  {categories.map((cat) => (
                    <option key={cat.id} value={cat.id}>
                      {cat.nombre}
                    </option>
                  ))}
                </select>
                <button
                  type="button"
                  onClick={() => setShowNewCatInput(!showNewCatInput)}
                  className="bg-blue-600 hover:bg-blue-500 text-white px-3 rounded-lg"
                >
                  +
                </button>
              </div>
            </div>
          </div>

          <div className="bg-slate-800/20 p-4 rounded-xl border border-white/5">
            <div className="grid grid-cols-2 md:grid-cols-6 gap-3">
              <div>
                <label className={labelStyle}>Costo</label>
                <input
                  type="number"
                  name="precioCosto"
                  value={formData.precioCosto}
                  className={inputStyle}
                  onChange={handleChange}
                />
              </div>
              <div>
                <label className={labelStyle}>Margen %</label>
                <input
                  type="number"
                  name="margenGanancia"
                  value={formData.margenGanancia}
                  className={inputStyle}
                  onChange={handleChange}
                />
              </div>
              <div>
                <label className={labelStyle}>IVA %</label>
                <input
                  type="number"
                  name="iva"
                  value={formData.iva}
                  className={inputStyle}
                  onChange={handleChange}
                />
              </div>
              <div>
                <label className={`${labelStyle} text-blue-400`}>Venta $</label>
                <input
                  type="number"
                  name="precioVenta"
                  value={formData.precioVenta}
                  className={`${inputStyle} border-blue-500/30 bg-blue-500/5 text-blue-400 font-bold`}
                  onChange={handleChange}
                />
              </div>
              <div>
                <label className={labelStyle}>Stock Act.</label>
                <input
                  type="number"
                  name="stockActual"
                  value={formData.stockActual}
                  className={inputStyle}
                  onChange={handleChange}
                />
              </div>
              <div>
                <label className={`${labelStyle} text-orange-400`}>
                  Stock Mín.
                </label>
                <input
                  type="number"
                  name="stockMinimo"
                  value={formData.stockMinimo}
                  className={`${inputStyle} border-orange-500/20 text-orange-200`}
                  onChange={handleChange}
                />
              </div>
            </div>
          </div>

          {productToEdit && (
            <div className="flex items-center gap-3 p-4 bg-orange-500/10 border border-orange-500/20 rounded-xl mb-4">
              <div className="flex-1">
                <h4 className="text-sm font-bold text-orange-400">
                  Estado del Producto
                </h4>
                <p className="text-[11px] text-slate-400 text-balance">
                  Si deshabilitas el producto, no aparecerá en las ventas pero
                  se mantendrá en el historial.
                </p>
              </div>
              <button
                type="button"
                onClick={() =>
                  setFormData((prev) => ({ ...prev, activo: !prev.activo }))
                }
                className={`px-4 py-2 rounded-lg text-xs font-bold transition-all ${
                  formData.activo
                    ? "bg-emerald-500/20 text-emerald-400 border border-emerald-500/40"
                    : "bg-red-500/20 text-red-400 border border-red-500/40"
                }`}
              >
                {formData.activo ? "ACTIVO" : "INACTIVO"}
              </button>
            </div>
          )}

          {/* ... botones de Guardar/Cancelar ... */}

          <div className="flex justify-end gap-3 pt-2">
            <button
              type="button"
              onClick={handleClose}
              className="px-5 py-2 text-sm text-slate-400 hover:text-white font-medium"
            >
              Cancelar
            </button>
            <button
              type="submit"
              className="bg-blue-600 hover:bg-blue-500 text-white px-10 py-2 rounded-xl text-sm font-bold shadow-lg shadow-blue-500/20 active:scale-95 transition-all"
            >
              Guardar Producto
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default AddProductModal;

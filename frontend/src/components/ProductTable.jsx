const ProductTable = ({ products = [], highlightedId, onEdit }) => {
  return (
    <div className="bg-slate-900 border border-white/5 rounded-2xl overflow-hidden">
      <table className="w-full text-left border-collapse">
        <thead>
          <tr className="bg-white/[0.02] text-slate-400 text-xs uppercase tracking-wider">
            <th className="p-4 font-medium">Nombre</th>
            <th className="p-4 font-medium">Descripción</th>
            <th className="p-4 font-medium">Precio</th>
            <th className="p-4 font-medium text-center">Stock</th>
            <th className="p-4 font-medium text-center">Estado</th>
            <th className="p-4 font-medium text-center">Categoría</th>
            <th className="p-4 font-medium text-center">Acciones</th>
          </tr>
        </thead>
        <tbody className="text-sm">
          {products && products.length > 0 ? (
            products.map((p) => (
              <tr
                key={p.id}
                className={`transition-all duration-700 ${
                  highlightedId === p.id
                    ? "bg-blue-500/20 border-l-4 border-blue-500 shadow-[0_0_15px_rgba(59,130,246,0.3)]"
                    : "border-b border-white/5"
                }`}
              >
                {/* 1. NOMBRE */}
                <td className="p-4 font-medium text-white">
                  {p.nombre || p.name}
                </td>

                {/* 2. DESCRIPCIÓN */}
                <td className="p-4 text-slate-400 text-xs truncate max-w-[200px]">
                  {p.descripcion || "Sin descripción"}
                </td>

                {/* 3. PRECIO */}
                <td className="p-4 text-white font-semibold">
                  ${p.precio || p.price || 0}
                </td>

                {/* 4. STOCK */}
                <td className="p-4 text-center">
                  <span
                    className={`px-3 py-1 rounded-full text-xs font-bold ${
                      (p.stockActual || 0) <= 5
                        ? "bg-red-500/10 text-red-500 border border-red-500/20"
                        : "bg-emerald-500/10 text-emerald-400 border border-emerald-500/20"
                    }`}
                  >
                    {p.stockActual !== undefined ? p.stockActual : 0} unid.
                  </span>
                </td>

                {/* 5. ESTADO */}
                <td className="p-4 text-center">
                  <span
                    className={`px-2 py-1 rounded-md text-[10px] font-bold uppercase tracking-wider ${
                      p.activo
                        ? "bg-blue-500/10 text-blue-400 border border-blue-500/20"
                        : "bg-slate-800 text-slate-500 border border-slate-700"
                    }`}
                  >
                    {p.activo ? "Activo" : "Inactivo"}
                  </span>
                </td>
                {/* 6. CATEGORIA */}
                <td className="p-4 text-center">
                  <span className="p-4 text-slate-400 text-xs truncate max-w-[200px]">
                    {p.categoriaProductos?.nombre || "Sin categoría"}
                  </span>
                </td>
                {/* 7. ACCIONES */}
                <td className="p-4 text-center">
                  <button
                    onClick={() => onEdit(p)}
                    className="p-2 hover:bg-white/10 rounded-lg text-slate-400 hover:text-blue-400 transition-colors"
                    title="Editar producto"
                  >
                    {/* Icono de Lápiz */}
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-5 w-5"
                      fill="none"
                      viewBox="0 0 24 24"
                      stroke="currentColor"
                    >
                      <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        strokeWidth={2}
                        d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z"
                      />
                    </svg>
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td
                colSpan="6"
                className="p-10 text-center text-slate-500 italic"
              >
                No hay productos en el inventario.
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default ProductTable;

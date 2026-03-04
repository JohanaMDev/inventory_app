const ProductTable = ({ products = [], highlightedId, onEdit }) => {
  return (
    <div className="bg-slate-900 border border-white/5 rounded-2xl overflow-hidden">
      <table className="w-full text-left border-collapse">
        <thead>
          <tr className="bg-white/[0.02] text-slate-400 text-xs uppercase tracking-wider">
            <th className="p-4 font-medium">Nombre</th>
            <th className="p-4 font-medium">Marca</th>
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

                {/* 2. MARCA */}
                <td className="p-4">{p.marca || "Genérico"}</td>

                {/* 3. DESCRIPCIÓN */}
                <td className="p-4 max-w-[180px]">
                  <p
                    className="text-xs text-slate-400 truncate"
                    title={p.descripcion}
                  >
                    {p.descripcion || "Sin descripción"}
                  </p>
                </td>

                {/* 4. PRECIO */}
                <td className="p-4 text-white font-semibold">
                  ${p.precio || p.price || 0}
                </td>

                {/* 5. STOCK */}
                <td className="p-4 text-center">
                  <div className="inline-flex flex-col items-center">
                    <span className="text-white font-bold">
                      {p.stockActual}
                    </span>
                    <span className="text-[9px] text-slate-500 uppercase">
                      unid.
                    </span>
                  </div>
                </td>

                {/* 6. ESTADO */}
                <td className="p-4 text-center">
                  <span
                    className={`text-[9px] px-2 py-0.5 rounded-md font-bold ${p.activo ? "bg-emerald-500/10 text-emerald-500 border border-emerald-500/20" : "bg-red-500/10 text-red-500 border border-red-500/20"}`}
                  >
                    {p.activo ? "ACTIVO" : "INACTIVO"}
                  </span>
                </td>

                {/* 7. CATEGORIA */}
                <td className="p-4 text-center">
                  <span className="p-4 text-slate-400 text-xs truncate max-w-[200px]">
                    {p.categoriaProductos?.nombre || "Sin categoría"}
                  </span>
                </td>

                {/* 8. ACCIONES */}
                <td className="p-4 text-center">
                  <button
                    onClick={() => onEdit(p)}
                    className="p-2 bg-blue-500/5 hover:bg-blue-500/20 border border-blue-500/10 hover:border-blue-500/40 rounded-lg text-blue-400 transition-all group"
                    title="Editar producto"
                  >
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-4 w-4 transform group-hover:scale-110 transition-transform"
                      fill="none"
                      viewBox="0 0 24 24"
                      stroke="currentColor"
                    >
                      <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        strokeWidth={2}
                        d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"
                      />
                    </svg>
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td
                colSpan="7"
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

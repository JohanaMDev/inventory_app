import { useState, useEffect, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Sidebar from "../layout/Sidebar";
import ProductTable from "../components/ProductTable";
import Button from "../components/Button";
import AddProductModal from "../components/AddProductModal";

const Products = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState("");
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [categories, setCategories] = useState([]);
  const [highlightedId, setHighlightedId] = useState(null); // Estado del brillo
  const navigate = useNavigate();

  // 1. CORRECCIÓN: Función unificada de recarga con brillo
  const handleRefresh = useCallback(async (newId) => {
    await fetchProducts(); // Recargamos la lista
    if (newId) {
      setHighlightedId(newId);
      setTimeout(() => setHighlightedId(null), 3000);
    }
  }, []);

  const fetchProducts = useCallback(async () => {
    const token = localStorage.getItem("token");
    if (!token) {
      navigate("/login");
      return;
    }
    try {
      const response = await axios.get("http://localhost:8080/api/productos", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setProducts(Array.isArray(response.data) ? response.data : []);
    } catch (error) {
      console.error("Error:", error);
      if (error.response?.status === 403) navigate("/login");
    } finally {
      setLoading(false);
    }
  }, [navigate]);

  const fetchCategories = useCallback(async () => {
    const token = localStorage.getItem("token");
    try {
      const response = await axios.get("http://localhost:8080/api/categorias", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setCategories(Array.isArray(response.data) ? response.data : []);
    } catch (error) {
      console.error("Error cargando categorías:", error);
    }
  }, []);

  useEffect(() => {
    fetchProducts();
    fetchCategories();
  }, [fetchProducts, fetchCategories]);

  const filteredProducts = products.filter((p) => {
    const search = searchTerm.toLowerCase();

    const matchNombre = (p.nombre || "").toLowerCase().includes(search);
    const matchDescripcion = (p.descripcion || "")
      .toLowerCase()
      .includes(search);
    const matchMarca = (p.marca || "").toLowerCase().includes(search);
    const matchCodigo = (p.codigoBarras || "").toLowerCase().includes(search);

    return matchNombre || matchDescripcion || matchMarca || matchCodigo;
  });

  const [selectedProduct, setSelectedProduct] = useState(null);

  const handleEdit = (product) => {
    setSelectedProduct(product); // Guardamos el producto a editar
    setIsModalOpen(true); // Abrimos el mismo modal
  };

  // Al cerrar el modal, hay que limpiar el seleccionado
  const handleCloseModal = () => {
    setIsModalOpen(false);
    setSelectedProduct(null);
  };

  return (
    <>
      <div className="flex min-h-screen bg-slate-950 text-white font-sans overflow-hidden">
        <Sidebar />
        <main className="flex-1 overflow-y-auto">
          <div className="max-w-[1400px] mx-auto p-8 lg:p-12">
            <header className="flex flex-col md:flex-row justify-between items-start md:items-center gap-6 mb-12">
              {/* Títulos y Subtítulos */}
              <div>
                <h1 className="text-4xl font-bold tracking-tight bg-gradient-to-r from-white to-slate-400 bg-clip-text text-transparent">
                  Gestión de Inventario
                </h1>
                <p className="text-slate-400 mt-2 flex items-center gap-2 text-sm">
                  <span className="w-2 h-2 rounded-full bg-emerald-500 animate-pulse"></span>
                  Visualizando stock real de Mendoza
                </p>
              </div>

              {/* Buscador y Botón de Acción */}
              <div className="flex flex-wrap items-center gap-4 w-full md:w-auto">
                <div className="relative w-full md:w-64">
                  <input
                    type="text"
                    placeholder="Buscar producto..."
                    className="w-full bg-slate-900/50 border border-white/10 rounded-xl px-4 py-2.5 text-sm text-white focus:outline-none focus:ring-2 focus:ring-blue-500/20 focus:border-blue-500 transition-all"
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                  />
                  {/* Opcional: Icono de lupa si tenés Lucide o similar, si no, el placeholder basta */}
                </div>

                <div className="w-full md:w-48">
                  <Button
                    variant="primary"
                    onClick={() => setIsModalOpen(true)}
                    className="w-full shadow-lg shadow-blue-500/20"
                  >
                    + Nuevo Producto
                  </Button>
                </div>
              </div>
            </header>

            <div className="relative">
              {loading ? (
                <div className="flex flex-col items-center justify-center h-64 gap-4">
                  <div className="w-12 h-12 border-4 border-blue-500/20 border-t-blue-500 rounded-full animate-spin"></div>
                  <p className="text-slate-500 font-medium text-sm">
                    Sincronizando con PostgreSQL...
                  </p>
                </div>
              ) : (
                <div className="overflow-x-auto rounded-2xl border border-white/5">
                  {/* 2. CORRECCIÓN: Pasamos el highlightedId a la tabla */}
                  <ProductTable
                    products={filteredProducts}
                    highlightedId={highlightedId}
                    onEdit={handleEdit}
                  />
                </div>
              )}
            </div>
          </div>
        </main>
      </div>

      {/* 3. CORRECCIÓN: Un solo modal con todas las props */}
      <AddProductModal
        isOpen={isModalOpen}
        onClose={handleCloseModal}
        onRefresh={handleRefresh}
        categories={categories}
        productToEdit={selectedProduct}
        initialData={selectedProduct} // Pasar los datos para editar
      />
    </>
  );
};

export default Products;

const InputField = ({
  label,
  type,
  value,
  onChange,
  placeholder,
  required = false,
}) => {
  return (
    <div>
      <label className="block text-slate-300 mb-2 text-sm font-medium">
        {label}
      </label>
      <input
        type={type}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        required={required}
        className="w-full bg-slate-800 border border-slate-700 rounded-lg px-4 py-3 text-white focus:outline-none focus:ring-2 focus:ring-blue-500 transition-all placeholder:text-slate-500"
      />
    </div>
  );
};

export default InputField;

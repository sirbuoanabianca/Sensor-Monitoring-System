
const minLengthValidator = (value, minLength) => {
    return value.length >= minLength;
};

const requiredValidator = value => {
    return value.trim() !== '';
};

const passValidator = value => {
    const re = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$/;
    return re.test(String(value));
};

const roleValidator = value => {
    return value === 'admin' || value === 'user';
};

const consumptionValidator = value => {
    return !isNaN(parseInt(value));
};

const validate = (value, rules) => {
    let isValid = true;

    for (let rule in rules) {

        switch (rule) {
            case 'minLength': isValid = isValid && minLengthValidator(value, rules[rule]);
                break;

            case 'isRequired': isValid = isValid && requiredValidator(value);
                break;

            case 'passValidator': isValid = isValid && passValidator(value);
                break;
            case 'roleValidator': isValid = isValid && roleValidator(value);
                break;
            case 'consumptionValidator': isValid = isValid && consumptionValidator(value);
                break;

            default: isValid = true;
        }

    }

    return isValid;
};

export default validate;

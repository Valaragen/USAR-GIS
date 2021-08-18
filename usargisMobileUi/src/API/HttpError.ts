class HttpError extends Error {
    statusCode: number;

    constructor(statusCode: number, message?: string) {
        super(message || statusCode.toString());

        if (Error.captureStackTrace) {
            Error.captureStackTrace(this, this.constructor);
        }

        this.name = this.constructor.name;

        this.statusCode = statusCode;
    }
}

export default HttpError;

export function BadRequestError(message?: string) {
    return new HttpError(400, message);
}

export function ForbiddenError(message?: string) {
    return new HttpError(403, message);
}

export function NotFoundError(message?: string) {
    return new HttpError(404, message);
}

export function InternalServerError(message?: string) {
    return new HttpError(500, message);
}
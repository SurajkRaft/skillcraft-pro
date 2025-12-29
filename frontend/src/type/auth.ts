// This matches your backend's SignupRequest DTO
export interface SignupRequest {
  email: string;
  password: string;
  displayName: string;
  storeSlug?: string;
  profileBio?: string;
}

// This matches your backend's successful signup response
export interface SignupResponse {
  message: string;
  creatorId: string;
  email: string;
  storeSlug?: string;
}

// For error responses from your GlobalExceptionHandler
export interface ApiError {
  [key: string]: string; // e.g., { "email": "Email is mandatory" }
}
